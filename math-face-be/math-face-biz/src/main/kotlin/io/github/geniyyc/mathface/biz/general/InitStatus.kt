package io.github.geniyyc.mathface.biz.general

import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.models.MfState
import io.github.geniyyc.mathface.cor.ICorChainDsl
import io.github.geniyyc.mathface.cor.worker

fun ICorChainDsl<MfContext>.initStatus(title: String) = worker {
    this.title = title
    this.description = "Инициализация статуса обработки"
    on { state == MfState.NONE }
    handle { state = MfState.RUNNING }
}
