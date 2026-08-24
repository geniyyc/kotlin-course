package io.github.geniyyc.mathface.repo.pg

object SqlFields {
    const val ID = "id"
    const val VALUE = "value"
    const val COMPLEXITY_ID = "complexity_id"
    const val DESCRIPTION = "description"
    const val OWNER_ID = "owner_id"
    const val LOCK = "lock"

    val allFields = listOf(ID, VALUE, COMPLEXITY_ID, DESCRIPTION, OWNER_ID, LOCK)
}

internal fun String.quoted() = "\"$this\""
