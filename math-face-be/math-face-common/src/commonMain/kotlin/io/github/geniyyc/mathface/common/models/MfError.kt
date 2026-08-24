package io.github.geniyyc.mathface.common.models

data class MfError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val exception: Throwable? = null,
)
