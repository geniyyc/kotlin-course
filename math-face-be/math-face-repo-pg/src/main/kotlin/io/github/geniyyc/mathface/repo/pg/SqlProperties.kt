package io.github.geniyyc.mathface.repo.pg

data class SqlProperties(
    val host: String = "localhost",
    val port: Int = 5432,
    val user: String = "postgres",
    val password: String = "math-face-pass",
    val database: String = "math_face",
    val schema: String = "public",
    val table: String = "expressions",
    val maxConnections: Int = 2,
) {
    val url: String
        get() = "postgresql://$host:$port/$database"
}
