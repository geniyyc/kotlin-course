plugins {
    id("build-jvm")
    alias(libs.plugins.openapi.generator)
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(libs.jackson.kotlin)
    implementation(libs.jackson.datatype)
    testImplementation(kotlin("test-junit"))
}

val specDir = layout.buildDirectory.dir("specs")
val specFile = rootProject.layout.projectDirectory.file("../math-face-other/math-face-specs/specs/specs-expression-v1.yaml")

tasks {
    val extractLibSpecs by registering(Copy::class) {
        description = "Копируем спецификацию во временную директорию"
        from(specFile)
        into(specDir)
    }

    val openApiGenerate by getting() {
        dependsOn(extractLibSpecs)
    }

    compileKotlin {
        dependsOn(openApiGenerate)
    }
}

openApiGenerate {
    val openapiGroup = "io.github.geniyyc.api.v1"
    generatorName.set("kotlin")
    packageName.set(openapiGroup)
    apiPackage.set("$openapiGroup.api")
    modelPackage.set("$openapiGroup.models")
    invokerPackage.set("$openapiGroup.invoker")

    inputSpec.set(specDir.map { it.file("specs-expression-v1.yaml").asFile.absolutePath })

    globalProperties.apply {
        put("models", "")
        put("modelDocs", "false")
    }

    configOptions.set(
        mapOf(
            "dateLibrary" to "string",
            "enumPropertyNaming" to "UPPERCASE",
            "serializationLibrary" to "jackson",
            "collectionType" to "list"
        )
    )
}

sourceSets {
    main {
        java.srcDir(layout.buildDirectory.dir("generate-resources/main/src/main/kotlin"))
    }
}
