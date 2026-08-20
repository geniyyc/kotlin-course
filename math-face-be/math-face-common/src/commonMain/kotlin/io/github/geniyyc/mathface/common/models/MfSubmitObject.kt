package io.github.geniyyc.mathface.common.models

data class MfSubmitObject(
    var expressionId: MfExpressionId = MfExpressionId.NONE,
    var answer: String = "",
)
