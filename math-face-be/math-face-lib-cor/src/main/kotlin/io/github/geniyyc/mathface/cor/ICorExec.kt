package io.github.geniyyc.mathface.cor

interface ICorExec<T> {
    val title: String
    val description: String
    suspend fun exec(context: T)
}
