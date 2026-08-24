package io.github.geniyyc.mathface.biz.stubs

import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.models.MfExpressionSet
import io.github.geniyyc.mathface.common.models.MfState
import io.github.geniyyc.mathface.common.models.MfStubs
import io.github.geniyyc.mathface.cor.ICorChainDsl
import io.github.geniyyc.mathface.cor.worker
import io.github.geniyyc.mathface.stubs.MfExpressionStub

fun ICorChainDsl<MfContext>.stubGenerateSuccess(title: String) = worker {
    this.title = title
    this.description = "Успешная генерация примеров (stub)"
    on { stubCase == MfStubs.SUCCESS && state == MfState.RUNNING }
    handle {
        expressionsResponse.add(
            MfExpressionStub.prepareExpressions(expressionFilterRequest.level)
        )
        state = MfState.FINISHING
    }
}
