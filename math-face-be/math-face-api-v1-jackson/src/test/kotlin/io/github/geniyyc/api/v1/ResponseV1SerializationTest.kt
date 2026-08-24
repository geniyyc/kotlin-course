package io.github.geniyyc.api.v1

import io.github.geniyyc.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseV1SerializationTest {
    private val response = ExpressionGenerateResponse(
        responseType = "generate",
        taskId = "task-123",
        expressions = listOf(
            ExpressionResponseObject(
                ownerId = "owner-1",
                expressions = listOf(
                    BaseExpression(
                        id = "1",
                        value = "2 + 4",
                        complexityId = 1,
                        description = "Simple addition"
                    )
                ),
                startTime = "2024-01-01T00:00:00Z"
            )
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(response)

        assertContains(json, "\"taskId\":\"task-123\"")
        assertContains(json, "\"value\":\"2 + 4\"")
        assertContains(json, "\"responseType\":\"generate\"")
        assertContains(json, "\"ownerId\":\"owner-1\"")
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(response)
        val obj = apiV1Mapper.readValue(json, IResponse::class.java) as ExpressionGenerateResponse

        assertEquals(response, obj)
    }
}
