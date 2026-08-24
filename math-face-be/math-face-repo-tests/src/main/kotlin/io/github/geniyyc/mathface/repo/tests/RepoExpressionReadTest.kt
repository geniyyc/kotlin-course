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

abstract class RepoExpressionReadTest {
    abstract val repo: IRepoExpression
    protected open val readSucc = initObjects[0]

    @Test
    fun readSuccess() = runRepoTest {
        val result = repo.readExpression(DbExpressionIdRequest(readSucc.id))
        assertIs<DbExpressionResponseOk>(result)
        assertEquals(readSucc, result.data)
    }

    @Test
    fun readNotFound() = runRepoTest {
        val result = repo.readExpression(DbExpressionIdRequest(notFoundId))
        assertIs<DbExpressionResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitExpressions("read") {
        override val initObjects: List<MfExpression> = listOf(
            createInitTestModel("read")
        )
        val notFoundId = MfExpressionId("expr-repo-read-notFound")
    }
}
