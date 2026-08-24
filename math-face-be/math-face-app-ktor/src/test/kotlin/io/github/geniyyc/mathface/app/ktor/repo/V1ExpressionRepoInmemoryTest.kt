package io.github.geniyyc.mathface.app.ktor.repo

import com.fasterxml.jackson.databind.DeserializationFeature
import io.github.geniyyc.api.v1.models.ExpressionDebug
import io.github.geniyyc.api.v1.models.ExpressionGenerateRequest
import io.github.geniyyc.api.v1.models.ExpressionGenerateResponse
import io.github.geniyyc.api.v1.models.ExpressionRequestDebugMode
import io.github.geniyyc.api.v1.models.ExpressionSubmitRequest
import io.github.geniyyc.api.v1.models.ExpressionSubmitResponse
import io.github.geniyyc.api.v1.models.GenerateObject
import io.github.geniyyc.api.v1.models.ResponseResult
import io.github.geniyyc.api.v1.models.SubmitObject
import io.github.geniyyc.mathface.app.ktor.MfAppSettings
import io.github.geniyyc.mathface.app.ktor.module
import io.github.geniyyc.mathface.common.MfCorSettings
import io.github.geniyyc.mathface.common.models.MfComplexityId
import io.github.geniyyc.mathface.common.models.MfExpression
import io.github.geniyyc.mathface.common.models.MfExpressionId
import io.github.geniyyc.mathface.repo.common.ExpressionRepoInitialized
import io.github.geniyyc.mathface.repo.inmemory.ExpressionRepoInMemory
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.jackson.jackson
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class V1ExpressionRepoInmemoryTest {

    private val uuidNew = "10000000-0000-0000-0000-000000000001"
    private val initExpression = MfExpression(
        id = MfExpressionId("expr-inmemory-1"),
        value = "2 + 2 =",
        complexityId = MfComplexityId(1),
        description = "Init expression",
    )

    private fun appSettings(repo: ExpressionRepoInMemory) = MfAppSettings(
        corSettings = MfCorSettings(
            repoTest = ExpressionRepoInitialized(repo, initObjects = listOf(initExpression)),
        )
    )

    @Test
    fun generateTestSuccess() = testApplication {
        application {
            module(
                appSettings(
                    ExpressionRepoInMemory(randomUuid = { uuidNew })
                )
            )
        }
        val client = createClient {
            install(ContentNegotiation) {
                jackson {
                    disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                }
            }
        }

        val response = client.post("/v1/expression/generate") {
            contentType(ContentType.Application.Json)
            setBody(
                ExpressionGenerateRequest(
                    debug = ExpressionDebug(mode = ExpressionRequestDebugMode.TEST),
                    expression = GenerateObject(level = 1),
                )
            )
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val body = response.body<ExpressionGenerateResponse>()
        assertEquals(ResponseResult.SUCCESS, body.result)
        assertNotNull(body.expressions)
        assertEquals(1, body.expressions!!.size)
        assertNotNull(body.expressions!!.first().expressions)
    }

    @Test
    fun submitTestSuccess() = testApplication {
        application {
            module(
                appSettings(
                    ExpressionRepoInMemory(randomUuid = { uuidNew })
                )
            )
        }
        val client = createClient {
            install(ContentNegotiation) {
                jackson {
                    disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                }
            }
        }

        val response = client.post("/v1/expression/submit") {
            contentType(ContentType.Application.Json)
            setBody(
                ExpressionSubmitRequest(
                    debug = ExpressionDebug(mode = ExpressionRequestDebugMode.TEST),
                    expression = SubmitObject(
                        expressionId = initExpression.id.asString(),
                        answer = "4",
                    ),
                )
            )
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val body = response.body<ExpressionSubmitResponse>()
        assertEquals(ResponseResult.SUCCESS, body.result)
        assertEquals("LEVEL_UP", body.decision)
    }
}
