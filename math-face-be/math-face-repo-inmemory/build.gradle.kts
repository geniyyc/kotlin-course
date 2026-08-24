plugins {
    id("build-jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(libs.coroutines.core)
    implementation(libs.cache4k)
    implementation(libs.uuid)

    implementation(projects.mathFaceCommon)
    api(projects.mathFaceRepoCommon)

    testImplementation(kotlin("test-junit"))
    testImplementation(projects.mathFaceRepoTests)
}
