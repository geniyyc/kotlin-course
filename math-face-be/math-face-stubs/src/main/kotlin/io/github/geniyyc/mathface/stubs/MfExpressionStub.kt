package io.github.geniyyc.mathface.stubs

import io.github.geniyyc.mathface.common.models.MfComplexityId
import io.github.geniyyc.mathface.common.models.MfExpression
import io.github.geniyyc.mathface.common.models.MfExpressionId
import io.github.geniyyc.mathface.common.models.MfExpressionSet
import io.github.geniyyc.mathface.common.models.MfSubmitResponse
import io.github.geniyyc.mathface.common.models.MfUserId
import kotlinx.datetime.Clock

object MfExpressionStub {
    fun prepareExpressions(level: Int, count: Int = 5): MfExpressionSet = MfExpressionSet(
        ownerId = MfUserId("owner-stub"),
        expressions = (1..count).map {
            MfExpression(
                id = MfExpressionId("expr-$it"),
                value = "$it + $level =",
                complexityId = MfComplexityId(level),
                description = "Level $level example $it",
            )
        }.toMutableList(),
        startTime = Clock.System.now(),
    )

    fun prepareResult(level: Int): MfExpressionSet = prepareExpressions(level)

    val SUBMIT_SUCCESS = MfSubmitResponse(
        result = "LEVEL_UP",
        message = "Great job!",
        nextLevel = 2,
    )
}
