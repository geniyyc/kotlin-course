package io.github.geniyyc.mathface.biz.repo

import io.github.geniyyc.mathface.biz.MfExpressionProcessor
import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.MfCorSettings
import io.github.geniyyc.mathface.common.models.MfCommand
import io.github.geniyyc.mathface.common.models.MfComplexityId
import io.github.geniyyc.mathface.common.models.MfExpression
import io.github.geniyyc.mathface.common.models.MfExpressionFilter
import io.github.geniyyc.mathface.common.models.MfExpressionId
import io.github.geniyyc.mathface.common.models.MfState
import io.github.geniyyc.mathface.common.models.MfWorkMode
import io.github.geniyyc.mathface.common.repo.DbExpressionRequest
import io.github.geniyyc.mathface.common.repo.DbExpressionResponseOk
import io.github.geniyyc.mathface.common.repo.DbExpressionsResponseOk
import io.github.geniyyc.mathface.repo.tests.ExpressionRepositoryMock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BizRepoGenerateTest {

    private val uuidNew = "10000000-0000-0000-0000-000000000001"
    private val existingExpression = MfExpression(
        id = MfExpressionId("expr-existing"),
        value = "5 + 5 =",
        complexityId = MfComplexityId(5),
        description = "Existing",
    )

    @Test
    fun repoGenerateSearchEmptyAndCreateSuccessTest() = runTest {
        var createdCount = 0
        val repo = ExpressionRepositoryMock(
            invokeSearchExpression = { DbExpressionsResponseOk(emptyList()) },
            invokeCreateExpression = { rq: DbExpressionRequest ->
                createdCount++
                DbExpressionResponseOk(rq.expression.copy(id = MfExpressionId(uuidNew)))
            }
        )
        val settings = MfCorSettings(repoTest = repo)
        val processor = MfExpressionProcessor(settings)

        val ctx = MfContext(
            command = MfCommand.GENERATE,
            workMode = MfWorkMode.TEST,
            expressionFilterRequest = MfExpressionFilter(level = 5),
        )
        processor.exec(ctx)

        assertEquals(MfState.FINISHING, ctx.state)
        assertEquals(5, createdCount)
        assertEquals(1, ctx.expressionsResponse.size)
        assertEquals(5, ctx.expressionsResponse.first().expressions.size)
        assertNotEquals(MfExpressionId.NONE, ctx.expressionsResponse.first().expressions.first().id)
    }

    @Test
    fun repoGenerateSearchReturnsExistingTest() = runTest {
        var createdCount = 0
        val repo = ExpressionRepositoryMock(
            invokeSearchExpression = { DbExpressionsResponseOk(listOf(existingExpression)) },
            invokeCreateExpression = { _ ->
                createdCount++
                throw NotImplementedError()
            }
        )
        val settings = MfCorSettings(repoTest = repo)
        val processor = MfExpressionProcessor(settings)

        val ctx = MfContext(
            command = MfCommand.GENERATE,
            workMode = MfWorkMode.TEST,
            expressionFilterRequest = MfExpressionFilter(level = 5),
        )
        processor.exec(ctx)

        assertEquals(MfState.FINISHING, ctx.state)
        assertEquals(0, createdCount)
        assertEquals(1, ctx.expressionsResponse.size)
        assertEquals(existingExpression.id, ctx.expressionsResponse.first().expressions.first().id)
    }
}
