package io.github.geniyyc.mathface.biz.exceptions

import io.github.geniyyc.mathface.common.models.MfWorkMode

class MfDbNotConfiguredException(val workMode: MfWorkMode) : RuntimeException(
    "Database is not configured for work mode $workMode"
)
