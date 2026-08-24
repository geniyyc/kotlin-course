import org.gradle.kotlin.dsl.named
import ru.otus.otuskotlin.marketplace.plugin.DockerBuildTask

plugins {
    id("build-docker")
}

docker {
    images.register("Pg") {
        buildContext = project.layout.buildDirectory.dir("docker").get().toString()
        imageName = project.name
        imageTag = "${project.version}"
    }
}

group = "io.github.geniyyc.mathface.migration"
version = "0.1.0"

afterEvaluate {
    tasks {
        named("dockerBuildPg", DockerBuildTask::class) {
            doFirst {
                copy {
                    from("src/main/liquibase")
                    from("src/main/docker/Dockerfile")
                    into(buildContext)
                }
            }
        }
    }
}
