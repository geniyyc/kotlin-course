package io.github.geniyyc.mathface.biz.stubs

import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.helpers.errorValidation
import io.github.geniyyc.mathface.common.helpers.fail
import io.github.geniyyc.mathface.common.models.MfState
import io.github.geniyyc.mathface.common.models.MfStubs
import io.github.geniyyc.mathface.cor.ICorChainDsl
import io.github.geniyyc.mathface.cor.worker

fun ICorChainDsl<MfContext>.stubValidationBadId(title: String) = worker {
    this.title = title
    this.description = "Имитация ошибки идентификатора"
    on { stubCase == MfStubs.BAD_ID && state == MfState.RUNNING }
    handle {
        fail(errorValidation("expressionId", "bad-id", "Invalid expression id in stub"))
    }
}
