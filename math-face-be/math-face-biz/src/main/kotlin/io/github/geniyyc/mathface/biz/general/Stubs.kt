package io.github.geniyyc.mathface.biz.general

import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.models.MfState
import io.github.geniyyc.mathface.common.models.MfWorkMode
import io.github.geniyyc.mathface.cor.ICorChainDsl
import io.github.geniyyc.mathface.cor.chain

fun ICorChainDsl<MfContext>.stubs(title: String, block: ICorChainDsl<MfContext>.() -> Unit) = chain {
    this.title = title
    this.description = "Обработка заглушек"
    on { workMode == MfWorkMode.STUB && state == MfState.RUNNING }
    block()
}
