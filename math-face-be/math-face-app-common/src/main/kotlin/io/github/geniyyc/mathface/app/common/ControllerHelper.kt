package io.github.geniyyc.mathface.app.common

import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.helpers.asMfError
import io.github.geniyyc.mathface.common.models.MfCommand
import io.github.geniyyc.mathface.common.models.MfState
import kotlinx.datetime.Clock
import kotlin.reflect.KClass

suspend inline fun <T> IMfAppSettings.controllerHelper(
    crossinline getRequest: suspend MfContext.() -> Unit,
    crossinline toResponse: suspend MfContext.() -> T,
    clazz: KClass<*>,
    logId: String,
): T {
    val ctx = MfContext(
        timeStart = Clock.System.now(),
    )
    return try {
        ctx.getRequest()
        processor.exec(ctx)
        ctx.toResponse()
    } catch (e: Throwable) {
        ctx.state = MfState.FAILING
        ctx.errors.add(e.asMfError())
        processor.exec(ctx)
        if (ctx.command == MfCommand.NONE) {
            ctx.command = MfCommand.GENERATE
        }
        ctx.toResponse()
    }
}
