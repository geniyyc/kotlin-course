package io.github.geniyyc.mathface.repo.common

import io.github.geniyyc.mathface.common.models.MfExpression
import io.github.geniyyc.mathface.common.repo.DbExpressionFilterRequest
import io.github.geniyyc.mathface.common.repo.DbExpressionIdRequest
import io.github.geniyyc.mathface.common.repo.DbExpressionRequest
import io.github.geniyyc.mathface.common.repo.IDbExpressionResponse
import io.github.geniyyc.mathface.common.repo.IDbExpressionsResponse

class ExpressionRepoInitialized(
    private val repo: IRepoExpressionInitializable,
    initObjects: Collection<MfExpression> = emptyList()
) : IRepoExpressionInitializable {

    init {
        if (initObjects.isNotEmpty()) {
            repo.save(initObjects)
        }
    }

    override fun save(expressions: Collection<MfExpression>): Collection<MfExpression> = repo.save(expressions)

    override suspend fun createExpression(rq: DbExpressionRequest): IDbExpressionResponse = repo.createExpression(rq)
    override suspend fun readExpression(rq: DbExpressionIdRequest): IDbExpressionResponse = repo.readExpression(rq)
    override suspend fun updateExpression(rq: DbExpressionRequest): IDbExpressionResponse = repo.updateExpression(rq)
    override suspend fun deleteExpression(rq: DbExpressionIdRequest): IDbExpressionResponse = repo.deleteExpression(rq)
    override suspend fun searchExpression(rq: DbExpressionFilterRequest): IDbExpressionsResponse = repo.searchExpression(rq)
}
