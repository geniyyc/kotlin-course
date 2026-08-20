package io.github.geniyyc.mathface.common.models

import kotlinx.datetime.Instant
import io.github.geniyyc.mathface.common.NONE

data class MfSubmitResponse(
    var result: String = "",
    var message: String = "",
    var nextLevel: Int = 0,
    var endTime: Instant = Instant.NONE,
)
