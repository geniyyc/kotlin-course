plugins {
    id("build-jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(libs.coroutines.core)

    implementation(projects.mathFaceCommon)
    implementation(projects.mathFaceStubs)
    implementation(projects.mathFaceLibCor)

    testImplementation(kotlin("test-junit"))
    testImplementation(libs.coroutines.test)
    testImplementation(projects.mathFaceRepoTests)
}
