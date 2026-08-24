package io.github.geniyyc.mathface.biz.repo

import io.github.geniyyc.mathface.biz.MfExpressionProcessor
import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.MfCorSettings
import io.github.geniyyc.mathface.common.models.MfCommand
import io.github.geniyyc.mathface.common.models.MfComplexityId
import io.github.geniyyc.mathface.common.models.MfExpression
import io.github.geniyyc.mathface.common.models.MfExpressionId
import io.github.geniyyc.mathface.common.models.MfState
import io.github.geniyyc.mathface.common.models.MfSubmitObject
import io.github.geniyyc.mathface.common.models.MfWorkMode
import io.github.geniyyc.mathface.common.repo.DbExpressionIdRequest
import io.github.geniyyc.mathface.common.repo.DbExpressionResponseOk
import io.github.geniyyc.mathface.repo.tests.ExpressionRepositoryMock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoSubmitTest {

    private val expressionId = MfExpressionId("expr-read-1")
    private val expression = MfExpression(
        id = expressionId,
        value = "2 + 2 =",
        complexityId = MfComplexityId(1),
        description = "Test expression",
    )

    @Test
    fun repoSubmitCorrectAnswerTest() = runTest {
        val repo = ExpressionRepositoryMock(
            invokeReadExpression = { DbExpressionResponseOk(expression) }
        )
        val settings = MfCorSettings(repoTest = repo)
        val processor = MfExpressionProcessor(settings)

        val ctx = MfContext(
            command = MfCommand.SUBMIT,
            workMode = MfWorkMode.TEST,
            submitRequest = MfSubmitObject(expressionId = expressionId, answer = "4"),
        )
        processor.exec(ctx)

        assertEquals(MfState.FINISHING, ctx.state)
        assertEquals("LEVEL_UP", ctx.submitResponse.result)
        assertEquals(2, ctx.submitResponse.nextLevel)
    }

    @Test
    fun repoSubmitWrongAnswerTest() = runTest {
        val repo = ExpressionRepositoryMock(
            invokeReadExpression = { DbExpressionResponseOk(expression) }
        )
        val settings = MfCorSettings(repoTest = repo)
        val processor = MfExpressionProcessor(settings)

        val ctx = MfContext(
            command = MfCommand.SUBMIT,
            workMode = MfWorkMode.TEST,
            submitRequest = MfSubmitObject(expressionId = expressionId, answer = "5"),
        )
        processor.exec(ctx)

        assertEquals(MfState.FINISHING, ctx.state)
        assertEquals("TRY_AGAIN", ctx.submitResponse.result)
        assertEquals(1, ctx.submitResponse.nextLevel)
    }
}
