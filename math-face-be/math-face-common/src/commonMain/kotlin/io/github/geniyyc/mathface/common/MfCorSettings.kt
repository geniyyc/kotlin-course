package io.github.geniyyc.mathface.common

import io.github.geniyyc.mathface.common.repo.IRepoExpression

data class MfCorSettings(
    val repoStub: IRepoExpression = IRepoExpression.NONE,
    val repoTest: IRepoExpression = IRepoExpression.NONE,
    val repoProd: IRepoExpression = IRepoExpression.NONE,
) {
    companion object {
        val NONE = MfCorSettings()
    }
}
