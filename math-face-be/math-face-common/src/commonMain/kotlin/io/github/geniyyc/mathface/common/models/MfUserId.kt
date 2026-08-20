package io.github.geniyyc.mathface.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class MfUserId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = MfUserId("")
    }
}
