package io.github.geniyyc.mathface.biz.validation

import io.github.geniyyc.mathface.biz.MfExpressionProcessor
import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.models.MfCommand
import io.github.geniyyc.mathface.common.models.MfExpressionFilter
import io.github.geniyyc.mathface.common.models.MfState
import io.github.geniyyc.mathface.common.models.MfWorkMode
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BizValidationGenerateTest {

    private val processor = MfExpressionProcessor()

    @Test
    fun emptyLevel() = runTest {
        val ctx = MfContext(
            command = MfCommand.GENERATE,
            workMode = MfWorkMode.TEST,
            expressionFilterRequest = MfExpressionFilter(level = 0),
        )
        processor.exec(ctx)
        assertEquals(MfState.FAILING, ctx.state)
        assertEquals("level", ctx.errors.first().field)
    }

    @Test
    fun levelOutOfRange() = runTest {
        val ctx = MfContext(
            command = MfCommand.GENERATE,
            workMode = MfWorkMode.TEST,
            expressionFilterRequest = MfExpressionFilter(level = 11),
        )
        processor.exec(ctx)
        assertEquals(MfState.FAILING, ctx.state)
        assertEquals("level", ctx.errors.first().field)
    }

    @Test
    fun validLevel() = runTest {
        val ctx = MfContext(
            command = MfCommand.GENERATE,
            workMode = MfWorkMode.TEST,
            expressionFilterRequest = MfExpressionFilter(level = 5),
        )
        processor.exec(ctx)
        assertEquals(MfState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
        assertEquals(5, ctx.expressionFilterValidated.level)
    }
}
