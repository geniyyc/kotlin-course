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

class BizRepoReadTest {

    private val expressionId = MfExpressionId("expr-read-1")
    private val expression = MfExpression(
        id = expressionId,
        value = "3 + 3 =",
        complexityId = MfComplexityId(2),
        description = "Test expression",
    )

    @Test
    fun repoReadSuccessTest() = runTest {
        var readId: MfExpressionId? = null
        val repo = ExpressionRepositoryMock(
            invokeReadExpression = { rq: DbExpressionIdRequest ->
                readId = rq.id
                DbExpressionResponseOk(expression)
            }
        )
        val settings = MfCorSettings(repoTest = repo)
        val processor = MfExpressionProcessor(settings)

        val ctx = MfContext(
            command = MfCommand.SUBMIT,
            workMode = MfWorkMode.TEST,
            submitRequest = MfSubmitObject(expressionId = expressionId, answer = "6"),
        )
        processor.exec(ctx)

        assertEquals(MfState.FINISHING, ctx.state)
        assertEquals(expressionId, readId)
        assertEquals("LEVEL_UP", ctx.submitResponse.result)
    }
}
