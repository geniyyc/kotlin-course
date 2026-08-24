package io.github.geniyyc.mathface.common.repo

import io.github.geniyyc.mathface.common.models.MfError
import io.github.geniyyc.mathface.common.models.MfExpression

sealed interface IDbExpressionsResponse : IDbResponse<List<MfExpression>>

data class DbExpressionsResponseOk(
    val data: List<MfExpression>
) : IDbExpressionsResponse

data class DbExpressionsResponseErr(
    val errors: List<MfError> = emptyList()
) : IDbExpressionsResponse {
    constructor(err: MfError) : this(listOf(err))
}
