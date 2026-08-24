package io.github.geniyyc.mathface.biz.repo

import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.helpers.fail
import io.github.geniyyc.mathface.common.models.MfState
import io.github.geniyyc.mathface.common.repo.DbExpressionFilterRequest
import io.github.geniyyc.mathface.common.repo.DbExpressionsResponseErr
import io.github.geniyyc.mathface.common.repo.DbExpressionsResponseOk
import io.github.geniyyc.mathface.cor.ICorChainDsl
import io.github.geniyyc.mathface.cor.worker

fun ICorChainDsl<MfContext>.repoSearch(title: String) = worker {
    this.title = title
    this.description = "Поиск выражений по уровню сложности"
    on { state == MfState.RUNNING }
    handle {
        val request = DbExpressionFilterRequest(level = expressionFilterValidated.level)
        when (val result = expressionRepo.searchExpression(request)) {
            is DbExpressionsResponseOk -> expressionsRepoDone = result.data.toMutableList()
            is DbExpressionsResponseErr -> fail(result.errors)
        }
    }
}
