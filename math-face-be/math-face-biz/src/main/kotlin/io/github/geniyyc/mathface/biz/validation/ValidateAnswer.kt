package io.github.geniyyc.mathface.biz.validation

import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.helpers.errorValidation
import io.github.geniyyc.mathface.common.helpers.fail
import io.github.geniyyc.mathface.cor.ICorChainDsl
import io.github.geniyyc.mathface.cor.worker

fun ICorChainDsl<MfContext>.validateAnswerNotEmpty(title: String) = worker {
    this.title = title
    this.description = "Проверка, что ответ пользователя задан"
    on { submitValidating.answer.isBlank() }
    handle {
        fail(errorValidation("answer", "empty", "Answer must not be empty"))
    }
}
