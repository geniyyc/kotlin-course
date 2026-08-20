package io.github.geniyyc.mathface.mappers.v1

import io.github.geniyyc.api.v1.models.BaseExpression
import io.github.geniyyc.api.v1.models.Error
import io.github.geniyyc.api.v1.models.ExpressionGenerateResponse
import io.github.geniyyc.api.v1.models.ExpressionResponseObject
import io.github.geniyyc.api.v1.models.ExpressionSubmitResponse
import io.github.geniyyc.api.v1.models.IResponse
import io.github.geniyyc.api.v1.models.ResponseResult
import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.NONE
import io.github.geniyyc.mathface.common.exceptions.UnknownMfCommand
import io.github.geniyyc.mathface.common.models.MfCommand
import io.github.geniyyc.mathface.common.models.MfComplexityId
import io.github.geniyyc.mathface.common.models.MfError
import io.github.geniyyc.mathface.common.models.MfExpression
import io.github.geniyyc.mathface.common.models.MfExpressionId
import io.github.geniyyc.mathface.common.models.MfExpressionSet
import io.github.geniyyc.mathface.common.models.MfState
import io.github.geniyyc.mathface.common.models.MfUserId
import kotlinx.datetime.Instant

fun MfContext.toTransportExpression(): IResponse = when (val cmd = command) {
    MfCommand.GENERATE -> toTransportGenerate()
    MfCommand.SUBMIT -> toTransportSubmit()
    MfCommand.NONE -> throw UnknownMfCommand(cmd)
}

fun MfContext.toTransportGenerate() = ExpressionGenerateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    taskId = taskId.takeIf { it.isNotBlank() },
    expressions = expressionsResponse.toTransportExpression(),
)

fun MfContext.toTransportSubmit() = ExpressionSubmitResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    decision = submitResponse.result.takeIf { it.isNotBlank() },
    message = submitResponse.message.takeIf { it.isNotBlank() },
    nextLevel = submitResponse.nextLevel.takeIf { it != 0 },
    endTime = submitResponse.endTime.toTransportInstant(),
)

fun List<MfExpressionSet>.toTransportExpression(): List<ExpressionResponseObject>? = this
    .map { it.toTransportExpression() }
    .toList()
    .takeIf { it.isNotEmpty() }

fun MfExpressionSet.toTransportExpression(): ExpressionResponseObject = ExpressionResponseObject(
    ownerId = ownerId.toTransportExpression(),
    expressions = expressions.toTransportBaseExpression(),
    startTime = startTime.toTransportInstant(),
)

fun List<MfExpression>.toTransportBaseExpression(): List<BaseExpression>? = this
    .map { it.toTransportBaseExpression() }
    .toList()
    .takeIf { it.isNotEmpty() }

fun MfExpression.toTransportBaseExpression(): BaseExpression = BaseExpression(
    id = id.toTransportExpression(),
    `value` = value.takeIf { it.isNotBlank() },
    complexityId = complexityId.toTransportExpression(),
    description = description.takeIf { it.isNotBlank() },
)

internal fun MfExpressionId.toTransportExpression() = takeIf { it != MfExpressionId.NONE }?.asString()
internal fun MfComplexityId.toTransportExpression() = takeIf { it != MfComplexityId.NONE }?.asInt()
internal fun MfUserId.toTransportExpression() = takeIf { it != MfUserId.NONE }?.asString()
internal fun Instant.toTransportInstant() = takeIf { it != Instant.NONE }?.toString()

private fun List<MfError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportExpression() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun MfError.toTransportExpression() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)

private fun MfState.toResult(): ResponseResult? = when (this) {
    MfState.RUNNING -> ResponseResult.SUCCESS
    MfState.FAILING -> ResponseResult.ERROR
    MfState.FINISHING -> ResponseResult.SUCCESS
    MfState.NONE -> null
}
