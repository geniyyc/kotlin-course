package io.github.geniyyc.mathface.common.repo

import io.github.geniyyc.mathface.common.models.MfError
import io.github.geniyyc.mathface.common.models.MfExpressionId

const val ERROR_GROUP_REPO = "repo"

fun errorNotFound(id: MfExpressionId) = DbExpressionResponseErr(
    MfError(
        code = "$ERROR_GROUP_REPO-not-found",
        group = ERROR_GROUP_REPO,
        field = "id",
        message = "Object with ID: ${id.asString()} is not Found",
    )
)

val errorEmptyId = DbExpressionResponseErr(
    MfError(
        code = "$ERROR_GROUP_REPO-empty-id",
        group = ERROR_GROUP_REPO,
        field = "id",
        message = "Id must not be null or blank"
    )
)
