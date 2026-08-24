plugins {
    id("build-jvm")
    id("maven-publish")
}

val specsZip = tasks.register<Zip>("specsZip") {
    description = "Упаковка спецификаций в Zip-архив"
    archiveClassifier.set("spec")
    archiveExtension.set("zip")
    from("specs")
}

configurations {
    runtimeElements {
        outgoing.artifact(specsZip)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()

            artifact(specsZip) {
                classifier = "spec"
                extension = "zip"
            }
        }
    }
}
