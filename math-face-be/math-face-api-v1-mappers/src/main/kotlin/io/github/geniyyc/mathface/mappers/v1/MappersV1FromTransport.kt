package io.github.geniyyc.mathface.mappers.v1

import io.github.geniyyc.api.v1.models.ExpressionDebug
import io.github.geniyyc.api.v1.models.ExpressionGenerateRequest
import io.github.geniyyc.api.v1.models.ExpressionRequestDebugMode
import io.github.geniyyc.api.v1.models.ExpressionRequestDebugStubs
import io.github.geniyyc.api.v1.models.ExpressionSubmitRequest
import io.github.geniyyc.api.v1.models.GenerateObject
import io.github.geniyyc.api.v1.models.IRequest
import io.github.geniyyc.api.v1.models.SubmitAnswerObject
import io.github.geniyyc.api.v1.models.SubmitObject
import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.models.MfCommand
import io.github.geniyyc.mathface.common.models.MfExpressionFilter
import io.github.geniyyc.mathface.common.models.MfExpressionId
import io.github.geniyyc.mathface.common.models.MfSolution
import io.github.geniyyc.mathface.common.models.MfStubs
import io.github.geniyyc.mathface.common.models.MfSubmitObject
import io.github.geniyyc.mathface.common.models.MfWorkMode
import io.github.geniyyc.mathface.mappers.v1.exceptions.UnknownRequestClass

fun MfContext.fromTransport(request: IRequest) = when (request) {
    is ExpressionGenerateRequest -> fromTransport(request)
    is ExpressionSubmitRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun String?.toExpressionId() = this?.let { MfExpressionId(it) } ?: MfExpressionId.NONE

private fun ExpressionDebug?.transportToWorkMode(): MfWorkMode = when (this?.mode) {
    ExpressionRequestDebugMode.PROD -> MfWorkMode.PROD
    ExpressionRequestDebugMode.TEST -> MfWorkMode.TEST
    ExpressionRequestDebugMode.STUB -> MfWorkMode.STUB
    null -> MfWorkMode.PROD
}

private fun ExpressionDebug?.transportToStubCase(): MfStubs = when (this?.stub) {
    ExpressionRequestDebugStubs.SUCCESS -> MfStubs.SUCCESS
    ExpressionRequestDebugStubs.NOT_FOUND -> MfStubs.NOT_FOUND
    ExpressionRequestDebugStubs.BAD_ID -> MfStubs.BAD_ID
    ExpressionRequestDebugStubs.BAD_LEVEL -> MfStubs.BAD_LEVEL
    ExpressionRequestDebugStubs.BAD_VALUE -> MfStubs.BAD_VALUE
    ExpressionRequestDebugStubs.CANNOT_DELETE -> MfStubs.CANNOT_DELETE
    ExpressionRequestDebugStubs.BAD_SEARCH_STRING -> MfStubs.BAD_SEARCH_STRING
    null -> MfStubs.NONE
}

fun MfContext.fromTransport(request: ExpressionGenerateRequest) {
    command = MfCommand.GENERATE
    expressionFilterRequest = request.expression.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun GenerateObject?.toInternal(): MfExpressionFilter = MfExpressionFilter(
    level = this?.level ?: 0
)

fun MfContext.fromTransport(request: ExpressionSubmitRequest) {
    command = MfCommand.SUBMIT
    submitRequest = request.expression.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun SubmitObject?.toInternal(): MfSubmitObject = MfSubmitObject(
    groupId = this?.groupId ?: "",
    answers = this?.answers?.map { it.toInternal() }?.toMutableList() ?: mutableListOf(),
)

private fun SubmitAnswerObject?.toInternal(): MfSolution = MfSolution(
    expressionId = this?.expressionId.toExpressionId(),
    expressionValue = this?.expressionValue ?: "",
    solutionValue = this?.solutionValue ?: "",
    solutionTime = this?.solutionTime ?: "",
)
