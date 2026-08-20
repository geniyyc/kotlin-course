plugins {
    id("build-jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    api(kotlin("test-junit"))
    api(libs.coroutines.test)

    api(projects.mathFaceCommon)
    api(projects.mathFaceRepoCommon)
}
