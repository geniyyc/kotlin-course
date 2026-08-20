package io.github.geniyyc.mathface.biz.validation

import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.models.MfState
import io.github.geniyyc.mathface.cor.ICorChainDsl
import io.github.geniyyc.mathface.cor.chain

fun ICorChainDsl<MfContext>.validation(
    title: String = "Валидация",
    description: String = "Проверка корректности входных данных",
    block: ICorChainDsl<MfContext>.() -> Unit
) = chain {
    this.title = title
    this.description = description
    on { state == MfState.RUNNING }
    block()
}
