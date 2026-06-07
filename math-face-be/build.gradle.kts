plugins {
    id("build-jvm") apply false
}

group = "io.github.geniyyc"
version = "0.0.1"

subprojects {
    group = rootProject.group
    version = rootProject.version
}
