package io.github.geniyyc.mathface.biz

import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.models.MfCommand
import io.github.geniyyc.mathface.common.models.MfExpressionFilter
import io.github.geniyyc.mathface.common.models.MfExpressionId
import io.github.geniyyc.mathface.common.models.MfState
import io.github.geniyyc.mathface.common.models.MfStubs
import io.github.geniyyc.mathface.common.models.MfSubmitObject
import io.github.geniyyc.mathface.common.models.MfWorkMode
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MfExpressionProcessorTest {

    private val processor = MfExpressionProcessor()

    @Test
    fun generateSuccess() = runTest {
        val ctx = MfContext(
            command = MfCommand.GENERATE,
            workMode = MfWorkMode.STUB,
            stubCase = MfStubs.SUCCESS,
            expressionFilterRequest = MfExpressionFilter(level = 2),
        )
        processor.exec(ctx)

        assertEquals(MfState.FINISHING, ctx.state)
        assertEquals(1, ctx.expressionsResponse.size)
        assertEquals(2, ctx.expressionsResponse.first().expressions.first().complexityId.asInt())
    }

    @Test
    fun generateBadLevel() = runTest {
        val ctx = MfContext(
            command = MfCommand.GENERATE,
            workMode = MfWorkMode.STUB,
            stubCase = MfStubs.BAD_LEVEL,
            expressionFilterRequest = MfExpressionFilter(level = 2),
        )
        processor.exec(ctx)

        assertEquals(MfState.FAILING, ctx.state)
        assertEquals("level", ctx.errors.first().field)
    }

    @Test
    fun submitSuccess() = runTest {
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
    fun submitBadId() = runTest {
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
}
