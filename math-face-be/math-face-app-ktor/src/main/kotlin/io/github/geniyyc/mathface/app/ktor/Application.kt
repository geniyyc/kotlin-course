package io.github.geniyyc.mathface.app.ktor

import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.github.geniyyc.mathface.app.ktor.v1.v1Expression
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.autohead.AutoHeadResponse
import io.ktor.server.plugins.cachingheaders.CachingHeaders
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.plugins.defaultheaders.DefaultHeaders
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun Application.module(appSettings: MfAppSettings = MfAppSettings()) {
    install(CORS) {
        anyHost()
    }
    install(DefaultHeaders)
    install(AutoHeadResponse)
    install(CachingHeaders)
    install(CallLogging)

    install(ContentNegotiation) {
        jackson {
            enable(MapperFeature.USE_BASE_TYPE_AS_DEFAULT_IMPL)
            registerModule(KotlinModule.Builder().build())
        }
    }

    routing {
        route("v1") {
            v1Expression(appSettings)
        }
    }
}
