import io.github.geniyyc.api.v1.models.ExpressionDebug
import io.github.geniyyc.api.v1.models.ExpressionGenerateRequest
import io.github.geniyyc.api.v1.models.ExpressionGenerateResponse
import io.github.geniyyc.api.v1.models.ExpressionRequestDebugMode
import io.github.geniyyc.api.v1.models.ExpressionRequestDebugStubs
import io.github.geniyyc.api.v1.models.ExpressionSubmitRequest
import io.github.geniyyc.api.v1.models.ExpressionSubmitResponse
import io.github.geniyyc.api.v1.models.GenerateObject
import io.github.geniyyc.api.v1.models.ResponseResult
import io.github.geniyyc.api.v1.models.SubmitAnswerObject
import io.github.geniyyc.api.v1.models.SubmitObject
import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.models.MfCommand
import io.github.geniyyc.mathface.common.models.MfComplexityId
import io.github.geniyyc.mathface.common.models.MfError
import io.github.geniyyc.mathface.common.models.MfExpression
import io.github.geniyyc.mathface.common.models.MfExpressionFilter
import io.github.geniyyc.mathface.common.models.MfExpressionId
import io.github.geniyyc.mathface.common.models.MfRequestId
import io.github.geniyyc.mathface.common.models.MfSolution
import io.github.geniyyc.mathface.common.models.MfState
import io.github.geniyyc.mathface.common.models.MfStubs
import io.github.geniyyc.mathface.common.models.MfSubmitObject
import io.github.geniyyc.mathface.common.models.MfSubmitResponse
import io.github.geniyyc.mathface.common.models.MfWorkMode
import io.github.geniyyc.mathface.mappers.v1.fromTransport
import io.github.geniyyc.mathface.mappers.v1.toTransportExpression
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperTest {

    @Test
    fun fromTransportGenerate() {
        val req = ExpressionGenerateRequest(
            debug = ExpressionDebug(
                mode = ExpressionRequestDebugMode.STUB,
                stub = ExpressionRequestDebugStubs.SUCCESS,
            ),
            expression = GenerateObject(level = 2)
        )

        val context = MfContext()
        context.fromTransport(req)

        assertEquals(MfStubs.SUCCESS, context.stubCase)
        assertEquals(MfWorkMode.STUB, context.workMode)
        assertEquals(MfExpressionFilter(level = 2), context.expressionFilterRequest)
        assertEquals(MfCommand.GENERATE, context.command)
    }

    @Test
    fun fromTransportSubmit() {
        val req = ExpressionSubmitRequest(
            debug = ExpressionDebug(
                mode = ExpressionRequestDebugMode.TEST,
                stub = ExpressionRequestDebugStubs.BAD_ID,
            ),
            expression = SubmitObject(
                groupId = "group-1",
                answers = listOf(
                    SubmitAnswerObject(
                        expressionId = "expr-1",
                        expressionValue = "2 + 2",
                        solutionValue = "42",
                        solutionTime = "2024-01-01T00:00:00Z"
                    )
                )
            )
        )

        val context = MfContext()
        context.fromTransport(req)

        assertEquals(MfStubs.BAD_ID, context.stubCase)
        assertEquals(MfWorkMode.TEST, context.workMode)
        assertEquals(MfCommand.SUBMIT, context.command)
        assertEquals("group-1", context.submitRequest.groupId)
        assertEquals(1, context.submitRequest.answers.size)
        assertEquals("expr-1", context.submitRequest.answers.first().expressionId.asString())
        assertEquals("2 + 2", context.submitRequest.answers.first().expressionValue)
    }

    @Test
    fun toTransportGenerate() {
        val context = MfContext(
            requestId = MfRequestId("1234"),
            command = MfCommand.GENERATE,
            taskId = "task-abc",
            expressionsResponse = mutableListOf(
                MfExpression(
                    id = MfExpressionId("expr-1"),
                    value = "2 + 2",
                    complexityId = MfComplexityId(1),
                    description = "Simple",
                )
            ),
            errors = mutableListOf(
                MfError(
                    code = "err",
                    group = "request",
                    field = "level",
                    message = "wrong level",
                )
            ),
            state = MfState.RUNNING,
        )

        val res = context.toTransportExpression() as ExpressionGenerateResponse

        assertEquals("task-abc", res.taskId)
        assertEquals(1, res.expressions?.size)
        assertEquals("expr-1", res.expressions?.firstOrNull()?.id)
        assertEquals(1, res.expressions?.firstOrNull()?.complexityId)
        assertEquals(ResponseResult.SUCCESS, res.result)
        assertEquals(1, res.errors?.size)
        assertEquals("err", res.errors?.firstOrNull()?.code)
    }

    @Test
    fun toTransportSubmit() {
        val context = MfContext(
            requestId = MfRequestId("5678"),
            command = MfCommand.SUBMIT,
            submitResponse = MfSubmitResponse(
                decision = "LEVEL_UP",
                message = "Good job!",
                nextLevel = 3,
            ),
            state = MfState.FINISHING,
        )

        val res = context.toTransportExpression() as ExpressionSubmitResponse

        assertEquals("LEVEL_UP", res.decision)
        assertEquals("Good job!", res.message)
        assertEquals(3, res.nextLevel)
        assertEquals(ResponseResult.SUCCESS, res.result)
    }
}
