dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

pluginManagement {
    includeBuild("../build-plugin")

    plugins {
        id("build-jvm")
        id("build-kmp") apply false
    }
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

// Включает typesafe project accessors
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":math-face-common")
include(":math-face-api-v1-jackson")
include(":math-face-api-v1-mappers")
include(":math-face-app-common")
include(":math-face-stubs")
include(":math-face-biz")
include(":math-face-app-ktor")
include(":math-face-app-kafka")
include(":math-face-lib-cor")

rootProject.name = "math-face-be"
