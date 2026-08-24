package io.github.geniyyc.mathface.biz.validation

import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.cor.ICorChainDsl
import io.github.geniyyc.mathface.cor.worker

fun ICorChainDsl<MfContext>.finishExpressionFilterValidation(title: String) = worker {
    this.title = title
    this.description = "Сохранение проверенного фильтра"
    handle {
        expressionFilterValidated = expressionFilterValidating
    }
}

fun ICorChainDsl<MfContext>.finishSubmitValidation(title: String) = worker {
    this.title = title
    this.description = "Сохранение проверенного ответа"
    handle {
        submitValidated = submitValidating
    }
}
