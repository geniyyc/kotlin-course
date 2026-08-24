plugins {
    id("build-jvm")
    id("build-docker")
}

val dockerDir = project.layout.buildDirectory.dir("docker-swagger").get().toString()
val specDir = project(":math-face-specs").layout.projectDirectory.dir("specs")

docker {
    images.register("Swagger") {
        buildContext = dockerDir
        dockerFile = "Dockerfile"
        dependsOnTask = "extractLibSpecs"
        imageName = project.name
        imageTag = "${project.version}"
    }
}

tasks {
    register<Copy>("extractLibSpecs") {
        description = "Подготовка директории для Dockerfile"
        from(specDir)
        from("Dockerfile", "generate-config.sh")
        into(dockerDir)
    }

    register("buildImages") {
        description = "Сборка докер-образов"
        group = "build"
        dependsOn("dockerBuildSwagger")
    }
}
