plugins {
    id("build-jvm")
    application
    alias(libs.plugins.shadow.jar)
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(libs.coroutines.core)

    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.serialization.jackson)
    implementation(libs.ktor.server.calllogging)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.default.headers)
    implementation(libs.ktor.server.auto.head.response)
    implementation(libs.ktor.server.caching.headers)
    implementation(libs.ktor.server.config.yaml)

    implementation(libs.jackson.kotlin)
    implementation(libs.jackson.datatype)
    implementation(libs.logback)

    implementation(projects.mathFaceCommon)
    implementation(projects.mathFaceAppCommon)
    implementation(projects.mathFaceApiV1Jackson)
    implementation(projects.mathFaceApiV1Mappers)
    implementation(projects.mathFaceBiz)

    testImplementation(kotlin("test-junit"))
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.ktor.client.content.negotiation)
    testImplementation(libs.ktor.client.jackson)
    testImplementation(libs.coroutines.test)
}

tasks {
    shadowJar {
        archiveBaseName.set("math-face-app-ktor")
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
