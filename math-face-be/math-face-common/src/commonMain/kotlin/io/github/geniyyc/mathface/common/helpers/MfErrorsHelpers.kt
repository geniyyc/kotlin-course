package io.github.geniyyc.mathface.common.helpers

import io.github.geniyyc.mathface.common.models.MfError

fun Throwable.asMfError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = MfError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)
