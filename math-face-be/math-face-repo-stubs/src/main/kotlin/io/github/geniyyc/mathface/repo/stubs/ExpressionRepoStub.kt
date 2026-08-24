package io.github.geniyyc.mathface.repo.stubs

import io.github.geniyyc.mathface.common.models.MfExpression
import io.github.geniyyc.mathface.common.repo.DbExpressionFilterRequest
import io.github.geniyyc.mathface.common.repo.DbExpressionIdRequest
import io.github.geniyyc.mathface.common.repo.DbExpressionRequest
import io.github.geniyyc.mathface.common.repo.DbExpressionsResponseOk
import io.github.geniyyc.mathface.common.repo.DbExpressionResponseOk
import io.github.geniyyc.mathface.common.repo.IDbExpressionResponse
import io.github.geniyyc.mathface.common.repo.IDbExpressionsResponse
import io.github.geniyyc.mathface.common.repo.IRepoExpression
import io.github.geniyyc.mathface.stubs.MfExpressionStub

class ExpressionRepoStub : IRepoExpression {
    override suspend fun createExpression(rq: DbExpressionRequest): IDbExpressionResponse {
        return DbExpressionResponseOk(MfExpressionStub.prepareExpressions(rq.expression.complexityId.asInt()).expressions.first())
    }

    override suspend fun readExpression(rq: DbExpressionIdRequest): IDbExpressionResponse {
        return DbExpressionResponseOk(MfExpressionStub.prepareExpressions(1).expressions.first())
    }

    override suspend fun updateExpression(rq: DbExpressionRequest): IDbExpressionResponse {
        return DbExpressionResponseOk(rq.expression)
    }

    override suspend fun deleteExpression(rq: DbExpressionIdRequest): IDbExpressionResponse {
        return DbExpressionResponseOk(MfExpressionStub.prepareExpressions(1).expressions.first())
    }

    override suspend fun searchExpression(rq: DbExpressionFilterRequest): IDbExpressionsResponse {
        return DbExpressionsResponseOk(MfExpressionStub.prepareExpressions(rq.level).expressions)
    }
}
