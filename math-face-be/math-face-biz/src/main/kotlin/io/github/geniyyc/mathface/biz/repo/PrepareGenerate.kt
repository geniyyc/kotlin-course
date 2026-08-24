package io.github.geniyyc.mathface.biz.repo

import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.models.MfState
import io.github.geniyyc.mathface.cor.ICorChainDsl
import io.github.geniyyc.mathface.cor.worker
import io.github.geniyyc.mathface.stubs.MfExpressionStub

fun ICorChainDsl<MfContext>.generateExpressionsIfEmpty(title: String) = worker {
    this.title = title
    this.description = "Генерация примеров, если в репозитории не найдено"
    on { state == MfState.RUNNING && command == io.github.geniyyc.mathface.common.models.MfCommand.GENERATE && expressionsRepoDone.isEmpty() }
    handle {
        expressionsRepoPrepare = MfExpressionStub.prepareExpressions(expressionFilterValidated.level).expressions
    }
}
