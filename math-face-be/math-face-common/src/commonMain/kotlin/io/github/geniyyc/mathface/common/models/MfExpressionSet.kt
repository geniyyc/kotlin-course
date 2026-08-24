package io.github.geniyyc.mathface.common.models

import kotlinx.datetime.Instant
import io.github.geniyyc.mathface.common.NONE


data class MfExpressionSet(
    var ownerId: MfUserId = MfUserId.NONE,
    var expressions: MutableList<MfExpression> = mutableListOf(),
    var startTime: Instant = Instant.NONE,
) {
    fun isEmpty() = this == NONE

    companion object {
        private val NONE = MfExpressionSet()
    }
}
