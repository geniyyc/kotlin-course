package io.github.geniyyc.mathface.app.ktor.v1

import io.github.geniyyc.mathface.app.common.IMfAppSettings
import io.ktor.server.application.call
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.v1Expression(appSettings: IMfAppSettings) {
    route("expression") {
        post("generate") {
            call.generateExpression(appSettings)
        }
        post("submit") {
            call.submitExpression(appSettings)
        }
    }
}
