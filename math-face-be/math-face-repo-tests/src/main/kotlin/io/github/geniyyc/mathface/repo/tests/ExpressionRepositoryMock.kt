package io.github.geniyyc.mathface.repo.tests

import io.github.geniyyc.mathface.common.repo.DbExpressionFilterRequest
import io.github.geniyyc.mathface.common.repo.DbExpressionIdRequest
import io.github.geniyyc.mathface.common.repo.DbExpressionRequest
import io.github.geniyyc.mathface.common.repo.IDbExpressionResponse
import io.github.geniyyc.mathface.common.repo.IDbExpressionsResponse
import io.github.geniyyc.mathface.common.repo.IRepoExpression

class ExpressionRepositoryMock(
    val invokeCreateExpression: suspend (DbExpressionRequest) -> IDbExpressionResponse = { throw NotImplementedError() },
    val invokeReadExpression: suspend (DbExpressionIdRequest) -> IDbExpressionResponse = { throw NotImplementedError() },
    val invokeUpdateExpression: suspend (DbExpressionRequest) -> IDbExpressionResponse = { throw NotImplementedError() },
    val invokeDeleteExpression: suspend (DbExpressionIdRequest) -> IDbExpressionResponse = { throw NotImplementedError() },
    val invokeSearchExpression: suspend (DbExpressionFilterRequest) -> IDbExpressionsResponse = { throw NotImplementedError() },
) : IRepoExpression {
    override suspend fun createExpression(rq: DbExpressionRequest): IDbExpressionResponse = invokeCreateExpression(rq)
    override suspend fun readExpression(rq: DbExpressionIdRequest): IDbExpressionResponse = invokeReadExpression(rq)
    override suspend fun updateExpression(rq: DbExpressionRequest): IDbExpressionResponse = invokeUpdateExpression(rq)
    override suspend fun deleteExpression(rq: DbExpressionIdRequest): IDbExpressionResponse = invokeDeleteExpression(rq)
    override suspend fun searchExpression(rq: DbExpressionFilterRequest): IDbExpressionsResponse = invokeSearchExpression(rq)
}
