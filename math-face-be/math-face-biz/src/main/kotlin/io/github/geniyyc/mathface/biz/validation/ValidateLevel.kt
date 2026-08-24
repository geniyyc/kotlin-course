package io.github.geniyyc.mathface.biz.validation

import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.helpers.errorValidation
import io.github.geniyyc.mathface.common.helpers.fail
import io.github.geniyyc.mathface.cor.ICorChainDsl
import io.github.geniyyc.mathface.cor.worker

fun ICorChainDsl<MfContext>.validateLevelNotEmpty(title: String) = worker {
    this.title = title
    this.description = "Проверка, что уровень сложности задан"
    on { expressionFilterValidating.level == 0 }
    handle {
        fail(errorValidation("level", "empty", "Level must not be empty"))
    }
}

fun ICorChainDsl<MfContext>.validateLevelProperRange(title: String) = worker {
    this.title = title
    this.description = "Проверка, что уровень сложности в допустимом диапазоне"
    on { expressionFilterValidating.level !in 1..10 }
    handle {
        fail(errorValidation("level", "range", "Level must be between 1 and 10"))
    }
}
