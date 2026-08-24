package io.github.geniyyc.mathface.common.models

data class MfComplexity(
    var id: MfComplexityId = MfComplexityId.NONE,
    var level: Int = 0,
    var template: String = "",
    var description: String = "",
    var maxValue: Int = 0,
) {
    fun isEmpty() = this == NONE

    companion object {
        private val NONE = MfComplexity()
    }
}
