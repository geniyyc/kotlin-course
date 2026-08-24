package io.github.geniyyc.mathface.app.ktor.plugins

import io.github.geniyyc.mathface.app.ktor.config.ConfigPaths
import io.github.geniyyc.mathface.app.ktor.config.PostgresConfig
import io.github.geniyyc.mathface.common.repo.IRepoExpression
import io.github.geniyyc.mathface.repo.inmemory.ExpressionRepoInMemory
import io.github.geniyyc.mathface.repo.pg.RepoExpressionSql
import io.github.geniyyc.mathface.repo.pg.SqlProperties
import io.github.geniyyc.mathface.repo.stubs.ExpressionRepoStub
import io.ktor.server.application.Application
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

enum class MfDbType(val confName: String) {
    PROD("prod"), TEST("test")
}

fun Application.getDatabaseConf(type: MfDbType): IRepoExpression {
    val dbSettingPath = "${ConfigPaths.repository}.${type.confName}"
    val dbSetting = environment.config.propertyOrNull(dbSettingPath)?.getString()?.lowercase()
    return when (dbSetting) {
        "in-memory", "inmemory", "memory", "mem" -> initInMemory()
        "stub" -> ExpressionRepoStub()
        "postgres", "postgresql", "pg", "sql", "psql" -> initPostgres()
        else -> initInMemory()
    }
}

fun Application.initPostgres(): IRepoExpression {
    val config = PostgresConfig(environment.config)
    return RepoExpressionSql(
        properties = SqlProperties(
            host = config.host,
            port = config.port,
            user = config.user,
            password = config.password,
            schema = config.schema,
            database = config.database,
        ),
    )
}

fun Application.initInMemory(): IRepoExpression {
    val ttlSetting = environment.config.propertyOrNull("db.prod")?.getString()?.let {
        Duration.parse(it)
    }
    return ExpressionRepoInMemory(ttl = ttlSetting ?: 10.minutes)
}
