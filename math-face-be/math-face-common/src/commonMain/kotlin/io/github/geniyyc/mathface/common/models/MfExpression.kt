package io.github.geniyyc.mathface.common.models

import kotlinx.datetime.Instant
import io.github.geniyyc.mathface.common.NONE

data class MfExpression(
    var id: MfExpressionId = MfExpressionId.NONE,
    var complexityId: MfComplexityId = MfComplexityId.NONE,
    var value: String = "",
    var description: String = "",
    var createTime: Instant = Instant.NONE,
) {
    fun isEmpty() = this == NONE

    companion object {
        private val NONE = MfExpression()
    }
}
