plugins {
    id("build-jvm")
    application
    alias(libs.plugins.shadow.jar)
}

application {
    mainClass.set("io.github.geniyyc.mathface.app.kafka.MainKt")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(libs.coroutines.core)
    implementation(libs.atomicfu)
    implementation(libs.kafka.client)
    implementation(libs.logback)

    implementation(projects.mathFaceCommon)
    implementation(projects.mathFaceAppCommon)
    implementation(projects.mathFaceApiV1Jackson)
    implementation(projects.mathFaceApiV1Mappers)
    implementation(projects.mathFaceBiz)

    testImplementation(kotlin("test-junit"))
    testImplementation(libs.coroutines.test)
}

tasks {
    shadowJar {
        archiveBaseName.set("math-face-app-kafka")
        archiveVersion.set("${rootProject.version}")
        archiveClassifier.set("all")
        manifest {
            attributes(
                mapOf(
                    "Main-Class" to application.mainClass.get()
                )
            )
        }
    }
}
