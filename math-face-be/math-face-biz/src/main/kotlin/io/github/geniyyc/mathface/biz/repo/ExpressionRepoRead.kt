package io.github.geniyyc.mathface.biz.repo

import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.helpers.fail
import io.github.geniyyc.mathface.common.models.MfState
import io.github.geniyyc.mathface.common.repo.DbExpressionIdRequest
import io.github.geniyyc.mathface.common.repo.DbExpressionResponseErr
import io.github.geniyyc.mathface.common.repo.DbExpressionResponseErrWithData
import io.github.geniyyc.mathface.common.repo.DbExpressionResponseOk
import io.github.geniyyc.mathface.cor.ICorChainDsl
import io.github.geniyyc.mathface.cor.worker

fun ICorChainDsl<MfContext>.repoRead(title: String) = worker {
    this.title = title
    this.description = "Чтение выражения из репозитория"
    on { state == MfState.RUNNING }
    handle {
        val request = DbExpressionIdRequest(submitValidated.expressionId)
        when (val result = expressionRepo.readExpression(request)) {
            is DbExpressionResponseOk -> expressionRepoDone = result.data
            is DbExpressionResponseErr -> fail(result.errors)
            is DbExpressionResponseErrWithData -> {
                fail(result.errors)
                expressionRepoDone = result.data
            }
        }
    }
}
