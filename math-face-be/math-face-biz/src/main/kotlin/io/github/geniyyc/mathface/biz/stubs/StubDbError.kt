package io.github.geniyyc.mathface.biz.stubs

import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.helpers.errorValidation
import io.github.geniyyc.mathface.common.helpers.fail
import io.github.geniyyc.mathface.common.models.MfState
import io.github.geniyyc.mathface.common.models.MfStubs
import io.github.geniyyc.mathface.cor.ICorChainDsl
import io.github.geniyyc.mathface.cor.worker

fun ICorChainDsl<MfContext>.stubDbError(title: String) = worker {
    this.title = title
    this.description = "Имитация ошибки базы данных"
    on { stubCase == MfStubs.CANNOT_DELETE && state == MfState.RUNNING }
    handle {
        fail(errorValidation("db", "db-error", "Database error in stub"))
    }
}
