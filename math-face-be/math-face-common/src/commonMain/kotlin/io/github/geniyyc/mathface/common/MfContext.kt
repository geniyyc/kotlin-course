package io.github.geniyyc.mathface.common

import kotlinx.datetime.Instant
import io.github.geniyyc.mathface.common.models.*

data class MfContext(
    var corSettings: MfCorSettings = MfCorSettings.NONE,
    var command: MfCommand = MfCommand.NONE,
    var state: MfState = MfState.NONE,
    val errors: MutableList<MfError> = mutableListOf(),

    var workMode: MfWorkMode = MfWorkMode.PROD,
    var stubCase: MfStubs = MfStubs.NONE,

    var requestId: MfRequestId = MfRequestId.NONE,
    var timeStart: Instant = Instant.NONE,

    var expressionFilterRequest: MfExpressionFilter = MfExpressionFilter(),
    var submitRequest: MfSubmitObject = MfSubmitObject(),

    var expressionFilterValidating: MfExpressionFilter = MfExpressionFilter(),
    var submitValidating: MfSubmitObject = MfSubmitObject(),

    var expressionFilterValidated: MfExpressionFilter = MfExpressionFilter(),
    var submitValidated: MfSubmitObject = MfSubmitObject(),

    var taskId: String = "",
    var expressionsResponse: MutableList<MfExpressionSet> = mutableListOf(),
    var submitResponse: MfSubmitResponse = MfSubmitResponse(),
)
