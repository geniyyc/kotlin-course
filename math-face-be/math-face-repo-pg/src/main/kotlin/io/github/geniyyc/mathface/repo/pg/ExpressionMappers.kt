package io.github.geniyyc.mathface.repo.pg

import io.github.geniyyc.mathface.common.models.MfExpression
import io.github.geniyyc.mathface.common.models.MfUserId
import io.github.smyrgeorge.sqlx4k.Statement

internal fun Statement.bindExpression(expression: MfExpression): Statement = apply {
    bind(SqlFields.ID, expression.id.asString())
    bind(SqlFields.VALUE, expression.value)
    bind(SqlFields.COMPLEXITY_ID, expression.complexityId.asInt())
    bind(SqlFields.DESCRIPTION, expression.description)
    bind(SqlFields.OWNER_ID, MfUserId("owner-pg").asString())
    bind(SqlFields.LOCK, expression.id.asString())
}
