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

rootProject.name = "math-face-be"
