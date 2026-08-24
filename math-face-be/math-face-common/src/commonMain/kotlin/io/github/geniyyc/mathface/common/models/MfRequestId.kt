package io.github.geniyyc.mathface.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class MfRequestId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = MfRequestId("")
    }
}
