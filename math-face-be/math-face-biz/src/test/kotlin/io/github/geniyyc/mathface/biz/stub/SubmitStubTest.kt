package io.github.geniyyc.mathface.biz.stub

import io.github.geniyyc.mathface.biz.MfExpressionProcessor
import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.models.MfCommand
import io.github.geniyyc.mathface.common.models.MfExpressionId
import io.github.geniyyc.mathface.common.models.MfState
import io.github.geniyyc.mathface.common.models.MfStubs
import io.github.geniyyc.mathface.common.models.MfSubmitObject
import io.github.geniyyc.mathface.common.models.MfWorkMode
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SubmitStubTest {

    private val processor = MfExpressionProcessor()

    @Test
    fun success() = runTest {
        val ctx = MfContext(
            command = MfCommand.SUBMIT,
            workMode = MfWorkMode.STUB,
            stubCase = MfStubs.SUCCESS,
            submitRequest = MfSubmitObject(expressionId = MfExpressionId("expr-1"), answer = "4"),
        )
        processor.exec(ctx)
        assertEquals(MfState.FINISHING, ctx.state)
        assertEquals("LEVEL_UP", ctx.submitResponse.result)
    }

    @Test
    fun badId() = runTest {
        val ctx = MfContext(
            command = MfCommand.SUBMIT,
            workMode = MfWorkMode.STUB,
            stubCase = MfStubs.BAD_ID,
            submitRequest = MfSubmitObject(expressionId = MfExpressionId("expr-1"), answer = "4"),
        )
        processor.exec(ctx)
        assertEquals(MfState.FAILING, ctx.state)
        assertEquals("expressionId", ctx.errors.first().field)
    }

    @Test
    fun badValue() = runTest {
        val ctx = MfContext(
            command = MfCommand.SUBMIT,
            workMode = MfWorkMode.STUB,
            stubCase = MfStubs.BAD_VALUE,
            submitRequest = MfSubmitObject(expressionId = MfExpressionId("expr-1"), answer = "4"),
        )
        processor.exec(ctx)
        assertEquals(MfState.FAILING, ctx.state)
        assertEquals("answer", ctx.errors.first().field)
    }
}
