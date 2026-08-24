package io.github.geniyyc.mathface.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class MfComplexityId(private val id: Int) {
    fun asInt() = id

    companion object {
        val NONE = MfComplexityId(0)
    }
}
