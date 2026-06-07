package io.github.geniyyc.mathface.common.models

data class MfSubmitObject(
    var groupId: String = "",
    val answers: MutableList<MfSolution> = mutableListOf(),
)
