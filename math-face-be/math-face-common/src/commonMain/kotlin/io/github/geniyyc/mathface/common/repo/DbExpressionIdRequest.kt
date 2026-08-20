package io.github.geniyyc.mathface.common.repo

import io.github.geniyyc.mathface.common.models.MfExpression
import io.github.geniyyc.mathface.common.models.MfExpressionId

data class DbExpressionIdRequest(
    val id: MfExpressionId,
) {
    constructor(expression: MfExpression) : this(expression.id)
}
