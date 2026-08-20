package io.github.geniyyc.mathface.biz.validation

import io.github.geniyyc.mathface.biz.MfExpressionProcessor
import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.models.MfCommand
import io.github.geniyyc.mathface.common.models.MfExpressionId
import io.github.geniyyc.mathface.common.models.MfState
import io.github.geniyyc.mathface.common.models.MfSubmitObject
import io.github.geniyyc.mathface.common.models.MfWorkMode
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BizValidationSubmitTest {

    private val processor = MfExpressionProcessor()

    @Test
    fun emptyExpressionId() = runTest {
        val ctx = MfContext(
            command = MfCommand.SUBMIT,
            workMode = MfWorkMode.TEST,
            submitRequest = MfSubmitObject(expressionId = MfExpressionId.NONE, answer = "4"),
        )
        processor.exec(ctx)
        assertEquals(MfState.FAILING, ctx.state)
        assertEquals("expressionId", ctx.errors.first().field)
    }

    @Test
    fun badExpressionIdFormat() = runTest {
        val ctx = MfContext(
            command = MfCommand.SUBMIT,
            workMode = MfWorkMode.TEST,
            submitRequest = MfSubmitObject(expressionId = MfExpressionId("bad id!!!"), answer = "4"),
        )
        processor.exec(ctx)
        assertEquals(MfState.FAILING, ctx.state)
        assertEquals("expressionId", ctx.errors.first().field)
    }

    @Test
    fun emptyAnswer() = runTest {
        val ctx = MfContext(
            command = MfCommand.SUBMIT,
            workMode = MfWorkMode.TEST,
            submitRequest = MfSubmitObject(expressionId = MfExpressionId("expr-1"), answer = ""),
        )
        processor.exec(ctx)
        assertEquals(MfState.FAILING, ctx.state)
        assertEquals("answer", ctx.errors.first().field)
    }

    @Test
    fun validSubmit() = runTest {
        val ctx = MfContext(
            command = MfCommand.SUBMIT,
            workMode = MfWorkMode.TEST,
            submitRequest = MfSubmitObject(expressionId = MfExpressionId("expr-1"), answer = "4"),
        )
        processor.exec(ctx)
        assertEquals(MfState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
        assertEquals("expr-1", ctx.submitValidated.expressionId.asString())
    }
}
