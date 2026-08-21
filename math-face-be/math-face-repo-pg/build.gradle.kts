import java.net.ServerSocket

plugins {
    id("build-jvm")
}

configurations.all {
    resolutionStrategy {
        force("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0")
        force("org.jetbrains.kotlinx:kotlinx-datetime-jvm:0.6.0")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(libs.coroutines.core)
    implementation(libs.uuid)
    implementation(libs.sqlx4k.postgres)
    implementation(libs.kotlinx.datetime)

    implementation(projects.mathFaceCommon)
    api(projects.mathFaceRepoCommon)

    testImplementation(kotlin("test-junit"))
    testImplementation(libs.coroutines.test)
    testImplementation(projects.mathFaceRepoTests)
    testImplementation(libs.kotlinx.datetime)
}

val pgContainerName = "math-face-pg-test"
val pgPassword = "math-face-pass"
val pgDatabase = "math_face"
val migrationFile = file("../../math-face-other/math-face-migration-pg/src/main/liquibase/changelog/changelog-v0.0.1.sql")

fun findFreePort(): Int = ServerSocket(0).use { socket -> socket.localPort }

fun org.gradle.api.Project.execDocker(vararg args: String): org.gradle.process.ExecResult {
    return exec {
        commandLine(listOf("docker") + args)
        isIgnoreExitValue = true
    }
}

val pgPortProvider = objects.property(Int::class.java)

val pgUp by tasks.registering {
    group = "db"
    notCompatibleWithConfigurationCache("Uses Project.exec")
    doFirst {
        val hostPort = findFreePort()
        pgPortProvider.set(hostPort)
        println("Starting PostgreSQL container on port $hostPort...")
        execDocker("rm", "-f", pgContainerName)
        val startResult = execDocker(
            "run", "-d",
            "--name", pgContainerName,
            "-p", "$hostPort:5432",
            "-e", "POSTGRES_PASSWORD=$pgPassword",
            "-e", "POSTGRES_USER=postgres",
            "-e", "POSTGRES_DB=$pgDatabase",
            "postgres"
        )
        if (startResult.exitValue != 0) error("Failed to start PostgreSQL container")

        println("Waiting for PostgreSQL to be ready...")
        var attempts = 0
        while (attempts < 30) {
            val readyResult = execDocker("exec", pgContainerName, "pg_isready")
            if (readyResult.exitValue == 0) break
            Thread.sleep(1000)
            attempts++
        }
        if (attempts >= 30) error("PostgreSQL container did not become ready")

        println("Applying migration...")
        execDocker(
            "cp", migrationFile.absolutePath, "$pgContainerName:/tmp/migration.sql"
        )
        val migrateResult = execDocker(
            "exec", pgContainerName,
            "psql", "-U", "postgres", "-d", pgDatabase, "-f", "/tmp/migration.sql"
        )
        if (migrateResult.exitValue != 0) error("Failed to apply migration")
        println("PostgreSQL is ready")
    }
}

val pgDn by tasks.registering {
    group = "db"
    notCompatibleWithConfigurationCache("Uses Project.exec")
    doFirst {
        println("Stopping PostgreSQL container...")
        execDocker("rm", "-f", pgContainerName)
    }
}

tasks.named<Test>("test") {
    dependsOn(pgUp)
    finalizedBy(pgDn)
    notCompatibleWithConfigurationCache("Depends on docker tasks")
    doFirst {
        environment("postgresPort", pgPortProvider.get().toString())
    }
}
