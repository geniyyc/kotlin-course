package io.github.geniyyc.mathface.repo.common

import io.github.geniyyc.mathface.common.models.MfExpression
import io.github.geniyyc.mathface.common.repo.IRepoExpression

interface IRepoExpressionInitializable : IRepoExpression {
    fun save(expressions: Collection<MfExpression>): Collection<MfExpression>
}
