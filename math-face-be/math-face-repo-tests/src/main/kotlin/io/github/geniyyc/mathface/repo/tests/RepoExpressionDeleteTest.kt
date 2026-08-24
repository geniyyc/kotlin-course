package io.github.geniyyc.mathface.repo.tests

import io.github.geniyyc.mathface.common.models.MfExpression
import io.github.geniyyc.mathface.common.models.MfExpressionId
import io.github.geniyyc.mathface.common.repo.DbExpressionIdRequest
import io.github.geniyyc.mathface.common.repo.DbExpressionResponseErr
import io.github.geniyyc.mathface.common.repo.DbExpressionResponseOk
import io.github.geniyyc.mathface.common.repo.IRepoExpression
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull

abstract class RepoExpressionDeleteTest {
    abstract val repo: IRepoExpression
    protected open val deleteSucc = initObjects[0]
    protected open val notFoundId = MfExpressionId("expr-repo-delete-notFound")

    @Test
    fun deleteSuccess() = runRepoTest {
        val result = repo.deleteExpression(DbExpressionIdRequest(deleteSucc.id))
        assertIs<DbExpressionResponseOk>(result)
        assertEquals(deleteSucc.value, result.data.value)
        assertEquals(deleteSucc.description, result.data.description)
    }

    @Test
    fun deleteNotFound() = runRepoTest {
        val result = repo.deleteExpression(DbExpressionIdRequest(notFoundId))
        assertIs<DbExpressionResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertNotNull(error)
    }

    companion object : BaseInitExpressions("delete") {
        override val initObjects: List<MfExpression> = listOf(
            createInitTestModel("delete"),
        )
    }
}
