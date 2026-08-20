package io.github.geniyyc.mathface.biz.stubs

import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.helpers.errorValidation
import io.github.geniyyc.mathface.common.helpers.fail
import io.github.geniyyc.mathface.common.models.MfState
import io.github.geniyyc.mathface.common.models.MfStubs
import io.github.geniyyc.mathface.cor.ICorChainDsl
import io.github.geniyyc.mathface.cor.worker

fun ICorChainDsl<MfContext>.stubValidationBadValue(title: String) = worker {
    this.title = title
    this.description = "Имитация ошибки значения ответа"
    on { stubCase == MfStubs.BAD_VALUE && state == MfState.RUNNING }
    handle {
        fail(errorValidation("answer", "bad-value", "Invalid answer value in stub"))
    }
}
