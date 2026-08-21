package io.github.geniyyc.mathface.biz.repo

import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.models.MfExpression
import io.github.geniyyc.mathface.common.models.MfState
import io.github.geniyyc.mathface.cor.ICorChainDsl
import io.github.geniyyc.mathface.cor.worker

fun ICorChainDsl<MfContext>.prepareSubmit(title: String) = worker {
    this.title = title
    this.description = "Подготовка объекта выражения для чтения из БД"
    on { state == MfState.RUNNING && command == io.github.geniyyc.mathface.common.models.MfCommand.SUBMIT }
    handle {
        expressionRepoPrepare = MfExpression(id = submitValidated.expressionId)
    }
}
