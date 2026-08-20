package io.github.geniyyc.mathface.common.helpers

import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.models.MfError
import io.github.geniyyc.mathface.common.models.MfState

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

fun MfContext.errorValidation(
    field: String,
    violationCode: String,
    description: String,
) = MfError(
    code = "validation-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for $field: $description",
)

fun MfContext.fail(error: MfError) {
    state = MfState.FAILING
    errors.add(error)
}
