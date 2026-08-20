package io.github.geniyyc.mathface.common.repo

import io.github.geniyyc.mathface.common.helpers.errorSystem

abstract class ExpressionRepoBase : IRepoExpression {

    protected suspend fun tryExpressionMethod(block: suspend () -> IDbExpressionResponse) = try {
        block()
    } catch (e: Throwable) {
        DbExpressionResponseErr(errorSystem("methodException", e = e))
    }

    protected suspend fun tryExpressionsMethod(block: suspend () -> IDbExpressionsResponse) = try {
        block()
    } catch (e: Throwable) {
        DbExpressionsResponseErr(errorSystem("methodException", e = e))
    }
}
