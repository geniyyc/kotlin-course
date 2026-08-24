package io.github.geniyyc.mathface.repo.tests

import io.github.geniyyc.mathface.common.models.MfComplexityId
import io.github.geniyyc.mathface.common.models.MfExpression
import io.github.geniyyc.mathface.common.models.MfExpressionId

abstract class BaseInitExpressions(private val op: String) : IInitObjects<MfExpression> {
    fun createInitTestModel(
        suf: String,
        level: Int = 1,
    ) = MfExpression(
        id = MfExpressionId("expr-repo-$op-$suf"),
        value = "$suf + $level =",
        complexityId = MfComplexityId(level),
        description = "$suf stub description",
    )
}
