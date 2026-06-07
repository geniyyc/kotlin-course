package io.github.geniyyc.mathface.common.models

data class MfSubmitResponse(
    var decision: String = "",
    var message: String = "",
    var nextLevel: Int = 0,
)
