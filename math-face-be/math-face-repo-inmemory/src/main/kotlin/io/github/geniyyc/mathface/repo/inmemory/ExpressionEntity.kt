package io.github.geniyyc.mathface.repo.inmemory

import io.github.geniyyc.mathface.common.NONE
import io.github.geniyyc.mathface.common.models.MfComplexityId
import io.github.geniyyc.mathface.common.models.MfExpression
import io.github.geniyyc.mathface.common.models.MfExpressionId
import kotlinx.datetime.Instant

data class ExpressionEntity(
    val id: String? = null,
    val value: String? = null,
    val complexityId: Int? = null,
    val description: String? = null,
    val createTime: String? = null,
) {
    constructor(model: MfExpression) : this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        value = model.value.takeIf { it.isNotBlank() },
        complexityId = model.complexityId.takeIf { it != MfComplexityId.NONE }?.asInt(),
        description = model.description.takeIf { it.isNotBlank() },
        createTime = model.createTime.takeIf { it != Instant.NONE }?.toString(),
    )

    fun toInternal() = MfExpression(
        id = id?.let { MfExpressionId(it) } ?: MfExpressionId.NONE,
        value = value ?: "",
        complexityId = complexityId?.let { MfComplexityId(it) } ?: MfComplexityId.NONE,
        description = description ?: "",
        createTime = createTime?.let { Instant.parse(it) } ?: Instant.NONE,
    )
}
