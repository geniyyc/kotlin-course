package io.github.geniyyc.mathface.biz.repo

import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.helpers.fail
import io.github.geniyyc.mathface.common.models.MfState
import io.github.geniyyc.mathface.common.repo.DbExpressionRequest
import io.github.geniyyc.mathface.common.repo.DbExpressionResponseErr
import io.github.geniyyc.mathface.common.repo.DbExpressionResponseErrWithData
import io.github.geniyyc.mathface.common.repo.DbExpressionResponseOk
import io.github.geniyyc.mathface.cor.ICorChainDsl
import io.github.geniyyc.mathface.cor.worker

fun ICorChainDsl<MfContext>.repoCreateExpressions(title: String) = worker {
    this.title = title
    this.description = "Сохранение сгенерированных выражений в репозиторий"
    on { state == MfState.RUNNING && expressionsRepoPrepare.isNotEmpty() }
    handle {
        val results = expressionsRepoPrepare.map { expressionRepo.createExpression(DbExpressionRequest(it)) }
        val errors = results.filterIsInstance<DbExpressionResponseErr>().flatMap { it.errors }
        if (errors.isNotEmpty()) {
            fail(errors)
        } else {
            expressionsRepoDone = results.filterIsInstance<DbExpressionResponseOk>().map { it.data }.toMutableList()
            results.filterIsInstance<DbExpressionResponseErrWithData>().forEach {
                fail(it.errors)
                expressionsRepoDone.add(it.data)
            }
        }
    }
}
