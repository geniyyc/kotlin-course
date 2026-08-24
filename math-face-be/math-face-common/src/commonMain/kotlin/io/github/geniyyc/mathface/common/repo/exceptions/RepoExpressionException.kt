package io.github.geniyyc.mathface.common.repo.exceptions

import io.github.geniyyc.mathface.common.models.MfExpressionId

open class RepoExpressionException(
    val expressionId: MfExpressionId,
    msg: String,
) : RepoException(msg)
