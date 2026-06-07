package io.github.geniyyc.mathface.common.models

data class MfSolution(
    var expressionId: MfExpressionId = MfExpressionId.NONE,
    var expressionValue: String = "",
    var solutionValue: String = "",
    var solutionTime: String = "",
)
