package io.github.geniyyc.api.v1

import io.github.geniyyc.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestV1SerializationTest {
    private val request = ExpressionGenerateRequest(
        requestType = "generate",
        debug = ExpressionDebug(
            mode = ExpressionRequestDebugMode.STUB,
            stub = ExpressionRequestDebugStubs.BAD_LEVEL
        ),
        expression = GenerateObject(
            level = 1
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, "\"level\":1")
        assertContains(json, "\"mode\":\"stub\"")
        assertContains(json, "\"stub\":\"badLevel\"")
        assertContains(json, "\"requestType\":\"generate\"")
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as ExpressionGenerateRequest

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"expression": null}
        """.trimIndent()
        val obj = apiV1Mapper.readValue(jsonString, ExpressionGenerateRequest::class.java)

        assertEquals(null, obj.expression)
    }
}
