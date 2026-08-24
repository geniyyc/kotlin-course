package io.github.geniyyc.mathface.repo.tests

import io.github.geniyyc.mathface.common.models.MfComplexityId
import io.github.geniyyc.mathface.common.models.MfExpression
import io.github.geniyyc.mathface.common.models.MfExpressionId
import io.github.geniyyc.mathface.common.repo.DbExpressionIdRequest
import io.github.geniyyc.mathface.common.repo.DbExpressionRequest
import io.github.geniyyc.mathface.common.repo.DbExpressionResponseErr
import io.github.geniyyc.mathface.common.repo.DbExpressionResponseOk
import io.github.geniyyc.mathface.common.repo.IRepoExpression
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

abstract class RepoExpressionUpdateTest {
    abstract val repo: IRepoExpression
    protected open val updateSucc = initObjects[0]
    protected val updateIdNotFound = MfExpressionId("expr-repo-update-not-found")

    private val reqUpdateSucc by lazy {
        updateSucc.copy(
            value = "update object",
            description = "update object description",
            complexityId = MfComplexityId(3),
        )
    }
    private val reqUpdateNotFound by lazy {
        MfExpression(
            id = updateIdNotFound,
            value = "update object not found",
            description = "update object not found description",
            complexityId = MfComplexityId(3),
        )
    }

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateExpression(DbExpressionRequest(reqUpdateSucc))
        assertIs<DbExpressionResponseOk>(result)
        assertEquals(reqUpdateSucc.id, result.data.id)
        assertEquals(reqUpdateSucc.value, result.data.value)
        assertEquals(reqUpdateSucc.description, result.data.description)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateExpression(DbExpressionRequest(reqUpdateNotFound))
        assertIs<DbExpressionResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitExpressions("update") {
        override val initObjects: List<MfExpression> = listOf(
            createInitTestModel("update"),
        )
    }
}
