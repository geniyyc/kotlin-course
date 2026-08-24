package io.github.geniyyc.mathface.repo.pg

import io.github.geniyyc.mathface.common.models.MfComplexityId
import io.github.geniyyc.mathface.common.models.MfExpression
import io.github.geniyyc.mathface.common.models.MfExpressionId
import io.github.smyrgeorge.sqlx4k.RowMapper
import io.github.smyrgeorge.sqlx4k.ValueEncoderRegistry
import io.github.smyrgeorge.sqlx4k.ResultSet

object MfExpressionRowMapper : RowMapper<MfExpression> {
    override fun map(row: ResultSet.Row, converters: ValueEncoderRegistry): MfExpression {
        return MfExpression(
            id = MfExpressionId(row.get(SqlFields.ID).asString() ?: ""),
            value = row.get(SqlFields.VALUE).asString() ?: "",
            complexityId = MfComplexityId(row.get(SqlFields.COMPLEXITY_ID).asString()?.toIntOrNull() ?: 0),
            description = row.get(SqlFields.DESCRIPTION).asString() ?: "",
        )
    }
}
