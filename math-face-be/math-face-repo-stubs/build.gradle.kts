plugins {
    id("build-jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(projects.mathFaceCommon)
    implementation(projects.mathFaceStubs)
}
