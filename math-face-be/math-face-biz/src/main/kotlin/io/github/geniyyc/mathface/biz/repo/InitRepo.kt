package io.github.geniyyc.mathface.biz.repo

import io.github.geniyyc.mathface.biz.exceptions.MfDbNotConfiguredException
import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.helpers.errorSystem
import io.github.geniyyc.mathface.common.helpers.fail
import io.github.geniyyc.mathface.common.models.MfWorkMode
import io.github.geniyyc.mathface.common.repo.IRepoExpression
import io.github.geniyyc.mathface.cor.ICorChainDsl
import io.github.geniyyc.mathface.cor.worker

fun ICorChainDsl<MfContext>.initRepo(title: String) = worker {
    this.title = title
    this.description = "Выбор рабочего репозитория в зависимости от режима"
    handle {
        expressionRepo = when {
            workMode == MfWorkMode.TEST -> corSettings.repoTest
            workMode == MfWorkMode.STUB -> corSettings.repoStub
            else -> corSettings.repoProd
        }
        if (workMode != MfWorkMode.STUB && expressionRepo == IRepoExpression.NONE) {
            fail(
                errorSystem(
                    violationCode = "dbNotConfigured",
                    e = MfDbNotConfiguredException(workMode)
                )
            )
        }
    }
}
