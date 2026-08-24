package io.github.geniyyc.mathface.repo.tests

import io.github.geniyyc.mathface.common.models.MfComplexityId
import io.github.geniyyc.mathface.common.models.MfExpression
import io.github.geniyyc.mathface.common.repo.DbExpressionFilterRequest
import io.github.geniyyc.mathface.common.repo.DbExpressionsResponseOk
import io.github.geniyyc.mathface.common.repo.IRepoExpression
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

abstract class RepoExpressionSearchTest {
    abstract val repo: IRepoExpression
    protected open val initializedObjects: List<MfExpression> = initObjects

    @Test
    fun searchByLevel() = runRepoTest {
        val result = repo.searchExpression(DbExpressionFilterRequest(level = searchLevel))
        assertIs<DbExpressionsResponseOk>(result)
        val expected = initializedObjects.filter { it.complexityId.asInt() == searchLevel }.sortedBy { it.id.asString() }
        assertEquals(expected, result.data.sortedBy { it.id.asString() })
    }

    companion object : BaseInitExpressions("search") {
        const val searchLevel = 2
        override val initObjects: List<MfExpression> = listOf(
            createInitTestModel("ad1", level = 1),
            createInitTestModel("ad2", level = 2),
            createInitTestModel("ad3", level = 2),
            createInitTestModel("ad4", level = 3),
        )
    }
}
