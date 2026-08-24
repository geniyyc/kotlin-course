package io.github.geniyyc.mathface.biz.general

import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.models.MfCommand
import io.github.geniyyc.mathface.common.models.MfState
import io.github.geniyyc.mathface.cor.ICorChainDsl
import io.github.geniyyc.mathface.cor.chain

fun ICorChainDsl<MfContext>.operation(title: String, command: MfCommand, block: ICorChainDsl<MfContext>.() -> Unit) = chain {
    this.title = title
    this.description = "Обработка команды $command"
    on { this@on.command == command && state == MfState.RUNNING }
    block()
}
