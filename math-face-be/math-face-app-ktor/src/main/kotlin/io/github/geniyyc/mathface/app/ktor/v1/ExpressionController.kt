package io.github.geniyyc.mathface.app.ktor.v1

import io.github.geniyyc.api.v1.models.ExpressionGenerateRequest
import io.github.geniyyc.api.v1.models.ExpressionGenerateResponse
import io.github.geniyyc.api.v1.models.ExpressionSubmitRequest
import io.github.geniyyc.api.v1.models.ExpressionSubmitResponse
import io.github.geniyyc.mathface.app.common.IMfAppSettings
import io.github.geniyyc.mathface.app.common.controllerHelper
import io.github.geniyyc.mathface.mappers.v1.fromTransport
import io.github.geniyyc.mathface.mappers.v1.toTransportExpression
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import io.ktor.server.response.respond

suspend fun ApplicationCall.generateExpression(appSettings: IMfAppSettings) {
    val request = receive<ExpressionGenerateRequest>()
    val response = appSettings.controllerHelper(
        getRequest = { fromTransport(request) },
        toResponse = { toTransportExpression() as ExpressionGenerateResponse },
        clazz = ExpressionGenerateRequest::class,
        logId = "expression-generate",
    )
    respond(response)
}

suspend fun ApplicationCall.submitExpression(appSettings: IMfAppSettings) {
    val request = receive<ExpressionSubmitRequest>()
    val response = appSettings.controllerHelper(
        getRequest = { fromTransport(request) },
        toResponse = { toTransportExpression() as ExpressionSubmitResponse },
        clazz = ExpressionSubmitRequest::class,
        logId = "expression-submit",
    )
    respond(response)
}
