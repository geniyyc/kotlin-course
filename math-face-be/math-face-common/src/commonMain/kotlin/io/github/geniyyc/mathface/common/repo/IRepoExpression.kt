package io.github.geniyyc.mathface.common.repo

interface IRepoExpression {
    suspend fun createExpression(rq: DbExpressionRequest): IDbExpressionResponse
    suspend fun readExpression(rq: DbExpressionIdRequest): IDbExpressionResponse
    suspend fun updateExpression(rq: DbExpressionRequest): IDbExpressionResponse
    suspend fun deleteExpression(rq: DbExpressionIdRequest): IDbExpressionResponse
    suspend fun searchExpression(rq: DbExpressionFilterRequest): IDbExpressionsResponse

    companion object {
        val NONE = object : IRepoExpression {
            override suspend fun createExpression(rq: DbExpressionRequest): IDbExpressionResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun readExpression(rq: DbExpressionIdRequest): IDbExpressionResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun updateExpression(rq: DbExpressionRequest): IDbExpressionResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun deleteExpression(rq: DbExpressionIdRequest): IDbExpressionResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun searchExpression(rq: DbExpressionFilterRequest): IDbExpressionsResponse {
                throw NotImplementedError("Must not be used")
            }
        }
    }
}
