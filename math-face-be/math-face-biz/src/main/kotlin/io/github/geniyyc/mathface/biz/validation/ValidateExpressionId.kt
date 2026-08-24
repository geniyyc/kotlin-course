package io.github.geniyyc.mathface.biz.validation

import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.helpers.errorValidation
import io.github.geniyyc.mathface.common.helpers.fail
import io.github.geniyyc.mathface.common.models.MfExpressionId
import io.github.geniyyc.mathface.cor.ICorChainDsl
import io.github.geniyyc.mathface.cor.worker

fun ICorChainDsl<MfContext>.validateExpressionIdNotEmpty(title: String) = worker {
    this.title = title
    this.description = "Проверка, что идентификатор выражения задан"
    on { submitValidating.expressionId == MfExpressionId.NONE }
    handle {
        fail(errorValidation("expressionId", "empty", "Expression id must not be empty"))
    }
}

private val idRegex = Regex("^[0-9a-zA-Z#:-]+$")

fun ICorChainDsl<MfContext>.validateExpressionIdProperFormat(title: String) = worker {
    this.title = title
    this.description = "Проверка формата идентификатора выражения"
    on { submitValidating.expressionId != MfExpressionId.NONE && !submitValidating.expressionId.asString().matches(idRegex) }
    handle {
        fail(errorValidation("expressionId", "format", "Expression id has invalid format"))
    }
}
