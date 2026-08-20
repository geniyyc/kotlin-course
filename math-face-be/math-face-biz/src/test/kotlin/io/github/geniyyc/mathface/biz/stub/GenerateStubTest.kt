package io.github.geniyyc.mathface.biz.stub

import io.github.geniyyc.mathface.biz.MfExpressionProcessor
import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.models.MfCommand
import io.github.geniyyc.mathface.common.models.MfExpressionFilter
import io.github.geniyyc.mathface.common.models.MfState
import io.github.geniyyc.mathface.common.models.MfStubs
import io.github.geniyyc.mathface.common.models.MfWorkMode
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GenerateStubTest {

    private val processor = MfExpressionProcessor()

    @Test
    fun success() = runTest {
        val ctx = MfContext(
            command = MfCommand.GENERATE,
            workMode = MfWorkMode.STUB,
            stubCase = MfStubs.SUCCESS,
            expressionFilterRequest = MfExpressionFilter(level = 2),
        )
        processor.exec(ctx)
        assertEquals(MfState.FINISHING, ctx.state)
        assertEquals(1, ctx.expressionsResponse.size)
    }

    @Test
    fun badLevel() = runTest {
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
    fun badId() = runTest {
        val ctx = MfContext(
            command = MfCommand.GENERATE,
            workMode = MfWorkMode.STUB,
            stubCase = MfStubs.BAD_ID,
            expressionFilterRequest = MfExpressionFilter(level = 2),
        )
        processor.exec(ctx)
        assertEquals(MfState.FAILING, ctx.state)
        assertEquals("expressionId", ctx.errors.first().field)
    }

    @Test
    fun dbError() = runTest {
        val ctx = MfContext(
            command = MfCommand.GENERATE,
            workMode = MfWorkMode.STUB,
            stubCase = MfStubs.CANNOT_DELETE,
            expressionFilterRequest = MfExpressionFilter(level = 2),
        )
        processor.exec(ctx)
        assertEquals(MfState.FAILING, ctx.state)
        assertEquals("db", ctx.errors.first().field)
    }
}
