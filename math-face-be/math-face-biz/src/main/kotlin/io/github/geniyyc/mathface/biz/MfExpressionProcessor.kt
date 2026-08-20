package io.github.geniyyc.mathface.biz

import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.MfCorSettings
import io.github.geniyyc.mathface.common.models.MfCommand
import io.github.geniyyc.mathface.common.models.MfError
import io.github.geniyyc.mathface.common.models.MfState
import io.github.geniyyc.mathface.common.models.MfStubs
import io.github.geniyyc.mathface.common.models.MfWorkMode
import io.github.geniyyc.mathface.stubs.MfExpressionStub

class MfExpressionProcessor(private val corSettings: MfCorSettings = MfCorSettings.NONE) {
    suspend fun exec(ctx: MfContext) {
        require(ctx.workMode == MfWorkMode.STUB) {
            "Only stub mode is supported in hw5"
        }
        when (ctx.command) {
            MfCommand.GENERATE -> execGenerateStub(ctx)
            MfCommand.SUBMIT -> execSubmitStub(ctx)
            MfCommand.NONE -> fail(ctx, "command", "Command is not set")
        }
    }

    private fun execGenerateStub(ctx: MfContext) {
        when (ctx.stubCase) {
            MfStubs.SUCCESS -> {
                ctx.expressionsResponse.add(MfExpressionStub.prepareResult(ctx.expressionFilterRequest.level))
                ctx.state = MfState.FINISHING
            }
            MfStubs.BAD_LEVEL -> fail(ctx, "level", "Invalid level")
            MfStubs.BAD_ID -> fail(ctx, "id", "Invalid id")
            MfStubs.NOT_FOUND -> fail(ctx, "", "Not found")
            MfStubs.BAD_VALUE -> fail(ctx, "value", "Invalid value")
            MfStubs.CANNOT_DELETE -> fail(ctx, "", "Cannot delete")
            MfStubs.BAD_SEARCH_STRING -> fail(ctx, "search", "Bad search string")
            MfStubs.NONE -> fail(ctx, "stub", "Stub case is not set")
        }
    }

    private fun execSubmitStub(ctx: MfContext) {
        when (ctx.stubCase) {
            MfStubs.SUCCESS -> {
                ctx.submitResponse = MfExpressionStub.SUBMIT_SUCCESS
                ctx.state = MfState.FINISHING
            }
            MfStubs.BAD_ID -> fail(ctx, "expressionId", "Invalid expression id")
            MfStubs.BAD_VALUE -> fail(ctx, "answer", "Invalid answer")
            MfStubs.BAD_LEVEL -> fail(ctx, "level", "Invalid level")
            MfStubs.NOT_FOUND -> fail(ctx, "expressionId", "Expression not found")
            MfStubs.CANNOT_DELETE -> fail(ctx, "", "Cannot delete")
            MfStubs.BAD_SEARCH_STRING -> fail(ctx, "search", "Bad search string")
            MfStubs.NONE -> fail(ctx, "stub", "Stub case is not set")
        }
    }

    private fun fail(ctx: MfContext, field: String, message: String) {
        ctx.state = MfState.FAILING
        ctx.errors.add(
            MfError(
                code = "stub-error",
                group = "stub",
                field = field,
                message = message,
            )
        )
    }
}
