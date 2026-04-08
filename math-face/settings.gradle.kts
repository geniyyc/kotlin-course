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
}

rootProject.name = "math-face"