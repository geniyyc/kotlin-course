plugins {
    id("build-jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(projects.mathFaceApiV1Jackson)
    implementation(projects.mathFaceCommon)

    testImplementation(kotlin("test-junit"))
}
