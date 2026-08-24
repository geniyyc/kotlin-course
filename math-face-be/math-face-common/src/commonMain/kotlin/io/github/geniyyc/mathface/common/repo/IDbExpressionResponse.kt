package io.github.geniyyc.mathface.common.repo

import io.github.geniyyc.mathface.common.models.MfError
import io.github.geniyyc.mathface.common.models.MfExpression

sealed interface IDbExpressionResponse : IDbResponse<MfExpression>

data class DbExpressionResponseOk(
    val data: MfExpression
) : IDbExpressionResponse

data class DbExpressionResponseErr(
    val errors: List<MfError> = emptyList()
) : IDbExpressionResponse {
    constructor(err: MfError) : this(listOf(err))
}

data class DbExpressionResponseErrWithData(
    val data: MfExpression,
    val errors: List<MfError> = emptyList()
) : IDbExpressionResponse {
    constructor(expression: MfExpression, err: MfError) : this(expression, listOf(err))
}
