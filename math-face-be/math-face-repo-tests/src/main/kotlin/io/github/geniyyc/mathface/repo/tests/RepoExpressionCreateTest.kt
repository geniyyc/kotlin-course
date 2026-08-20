package io.github.geniyyc.mathface.repo.tests

import io.github.geniyyc.mathface.common.models.MfComplexityId
import io.github.geniyyc.mathface.common.models.MfExpression
import io.github.geniyyc.mathface.common.models.MfExpressionId
import io.github.geniyyc.mathface.common.repo.DbExpressionRequest
import io.github.geniyyc.mathface.common.repo.DbExpressionResponseOk
import io.github.geniyyc.mathface.repo.common.IRepoExpressionInitializable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotEquals

abstract class RepoExpressionCreateTest {
    abstract val repo: IRepoExpressionInitializable
    protected open val uuidNew = MfExpressionId("10000000-0000-0000-0000-000000000001")

    private val createObj = MfExpression(
        value = "create object",
        complexityId = MfComplexityId(2),
        description = "create object description",
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createExpression(DbExpressionRequest(createObj))
        assertIs<DbExpressionResponseOk>(result)
        assertEquals(uuidNew, result.data.id)
        assertEquals(createObj.value, result.data.value)
        assertEquals(createObj.complexityId, result.data.complexityId)
        assertNotEquals(MfExpressionId.NONE, result.data.id)
    }

    companion object : BaseInitExpressions("create") {
        override val initObjects: List<MfExpression> = emptyList()
    }
}
