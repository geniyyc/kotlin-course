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

class BizRepoCreateTest {

    private val uuidNew = "10000000-0000-0000-0000-000000000001"

    @Test
    fun repoCreateSuccessTest() = runTest {
        val repo = ExpressionRepositoryMock(
            invokeSearchExpression = { DbExpressionsResponseOk(emptyList()) },
            invokeCreateExpression = { rq: DbExpressionRequest ->
                DbExpressionResponseOk(rq.expression.copy(id = MfExpressionId(uuidNew)))
            }
        )
        val settings = MfCorSettings(repoTest = repo)
        val processor = MfExpressionProcessor(settings)

        val ctx = MfContext(
            command = MfCommand.GENERATE,
            workMode = MfWorkMode.TEST,
            expressionFilterRequest = MfExpressionFilter(level = 2),
        )
        processor.exec(ctx)

        assertEquals(MfState.FINISHING, ctx.state)
        assertEquals(1, ctx.expressionsResponse.size)
        assertNotEquals(MfExpressionId.NONE, ctx.expressionsResponse.first().expressions.first().id)
        assertEquals(MfComplexityId(2), ctx.expressionsResponse.first().expressions.first().complexityId)
    }
}
