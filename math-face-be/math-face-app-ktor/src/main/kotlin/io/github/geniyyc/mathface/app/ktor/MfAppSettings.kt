package io.github.geniyyc.mathface.app.ktor

import io.github.geniyyc.mathface.app.common.IMfAppSettings
import io.github.geniyyc.mathface.biz.MfExpressionProcessor
import io.github.geniyyc.mathface.common.MfCorSettings

data class MfAppSettings(
    override val corSettings: MfCorSettings = MfCorSettings(),
    override val processor: MfExpressionProcessor = MfExpressionProcessor(corSettings),
) : IMfAppSettings
