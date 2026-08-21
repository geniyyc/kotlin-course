package io.github.geniyyc.mathface.repo.pg

import org.junit.Assume

object PostgresContainer {
    fun assumeJavaVersion() {
        val version = Runtime.version().feature()
        Assume.assumeTrue(
            "PostgreSQL repository tests require Java 21 or higher (sqlx4k requirement), current: $version",
            version >= 21
        )
    }

    fun sqlProperties(): SqlProperties = SqlProperties(
        host = "localhost",
        port = System.getenv("postgresPort")?.toIntOrNull() ?: 5432,
        user = "postgres",
        password = "math-face-pass",
        database = "math_face",
    )
}
