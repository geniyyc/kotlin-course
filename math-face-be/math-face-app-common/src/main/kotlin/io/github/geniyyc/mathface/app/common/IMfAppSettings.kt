package io.github.geniyyc.mathface.app.common

import io.github.geniyyc.mathface.biz.MfExpressionProcessor
import io.github.geniyyc.mathface.common.MfCorSettings

interface IMfAppSettings {
    val processor: MfExpressionProcessor
    val corSettings: MfCorSettings
}
