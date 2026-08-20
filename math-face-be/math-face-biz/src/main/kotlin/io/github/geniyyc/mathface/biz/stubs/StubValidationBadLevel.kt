package io.github.geniyyc.mathface.biz.stubs

import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.models.MfStubs
import io.github.geniyyc.mathface.common.models.MfState
import io.github.geniyyc.mathface.cor.ICorChainDsl
import io.github.geniyyc.mathface.cor.worker
import io.github.geniyyc.mathface.common.helpers.fail
import io.github.geniyyc.mathface.common.helpers.errorValidation

fun ICorChainDsl<MfContext>.stubValidationBadLevel(title: String) = worker {
    this.title = title
    this.description = "Имитация ошибки уровня сложности"
    on { stubCase == MfStubs.BAD_LEVEL && state == MfState.RUNNING }
    handle {
        fail(errorValidation("level", "bad-level", "Invalid level in stub"))
    }
}
