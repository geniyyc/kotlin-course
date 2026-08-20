plugins {
    id("build-jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(libs.coroutines.core)

    implementation(projects.mathFaceCommon)
    implementation(projects.mathFaceStubs)

    testImplementation(kotlin("test-junit"))
    testImplementation(libs.coroutines.test)
}
