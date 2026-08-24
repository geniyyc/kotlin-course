package io.github.geniyyc.mathface.app.ktor.plugins

import io.github.geniyyc.mathface.app.ktor.MfAppSettings
import io.github.geniyyc.mathface.biz.MfExpressionProcessor
import io.github.geniyyc.mathface.common.MfCorSettings
import io.ktor.server.application.Application

fun Application.initAppSettings(): MfAppSettings {
    val corSettings = MfCorSettings(
        repoTest = getDatabaseConf(MfDbType.TEST),
        repoProd = getDatabaseConf(MfDbType.PROD),
    )
    return MfAppSettings(
        corSettings = corSettings,
        processor = MfExpressionProcessor(corSettings),
    )
}
