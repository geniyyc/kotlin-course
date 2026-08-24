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
import io.github.geniyyc.mathface.common.repo.DbExpressionFilterRequest
import io.github.geniyyc.mathface.common.repo.DbExpressionsResponseOk
import io.github.geniyyc.mathface.repo.tests.ExpressionRepositoryMock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoSearchTest {

    private val searchLevel = 3
    private val foundExpression = MfExpression(
        id = MfExpressionId("expr-search-1"),
        value = "1 + 3 =",
        complexityId = MfComplexityId(searchLevel),
        description = "Found expression",
    )

    @Test
    fun repoSearchSuccessTest() = runTest {
        var searchedLevel: Int? = null
        val repo = ExpressionRepositoryMock(
            invokeSearchExpression = { rq: DbExpressionFilterRequest ->
                searchedLevel = rq.level
                DbExpressionsResponseOk(listOf(foundExpression))
            }
        )
        val settings = MfCorSettings(repoTest = repo)
        val processor = MfExpressionProcessor(settings)

        val ctx = MfContext(
            command = MfCommand.GENERATE,
            workMode = MfWorkMode.TEST,
            expressionFilterRequest = MfExpressionFilter(level = searchLevel),
        )
        processor.exec(ctx)

        assertEquals(MfState.FINISHING, ctx.state)
        assertEquals(searchLevel, searchedLevel)
        assertEquals(1, ctx.expressionsResponse.size)
        assertEquals(foundExpression.id, ctx.expressionsResponse.first().expressions.first().id)
    }
}
