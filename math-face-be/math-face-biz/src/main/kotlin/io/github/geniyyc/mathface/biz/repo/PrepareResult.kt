package io.github.geniyyc.mathface.biz.repo

import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.models.MfCommand
import io.github.geniyyc.mathface.common.models.MfExpression
import io.github.geniyyc.mathface.common.models.MfExpressionSet
import io.github.geniyyc.mathface.common.models.MfState
import io.github.geniyyc.mathface.common.models.MfSubmitResponse
import io.github.geniyyc.mathface.common.models.MfUserId
import io.github.geniyyc.mathface.common.models.MfWorkMode
import io.github.geniyyc.mathface.cor.ICorChainDsl
import io.github.geniyyc.mathface.cor.worker
import kotlinx.datetime.Clock

fun ICorChainDsl<MfContext>.prepareResult(title: String) = worker {
    this.title = title
    this.description = "Подготовка ответа клиенту"
    on { workMode != MfWorkMode.STUB && state == MfState.RUNNING }
    handle {
        when (command) {
            MfCommand.GENERATE -> {
                expressionsResponse.add(
                    MfExpressionSet(
                        ownerId = MfUserId("owner-prod"),
                        expressions = expressionsRepoDone.toMutableList(),
                        startTime = Clock.System.now(),
                    )
                )
            }
            MfCommand.SUBMIT -> {
                val expression = expressionRepoDone
                val isCorrect = evaluateAnswer(expression, submitValidated.answer)
                val currentLevel = expression.complexityId.asInt()
                submitResponse = MfSubmitResponse(
                    result = if (isCorrect) "LEVEL_UP" else "TRY_AGAIN",
                    message = if (isCorrect) "Great job!" else "Wrong answer",
                    nextLevel = if (isCorrect) currentLevel + 1 else currentLevel,
                    endTime = Clock.System.now(),
                )
            }
            MfCommand.NONE -> {}
        }
        state = MfState.FINISHING
    }
}

private fun evaluateAnswer(expression: MfExpression, answer: String): Boolean {
    val expected = expression.value
        .substringBefore("=")
        .trim()
        .split("+")
        .mapNotNull { it.trim().toIntOrNull() }
        .sum()
    return answer.trim() == expected.toString()
}
