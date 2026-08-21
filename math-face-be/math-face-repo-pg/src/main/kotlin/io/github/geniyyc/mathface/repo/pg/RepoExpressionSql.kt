package io.github.geniyyc.mathface.repo.pg

import com.benasher44.uuid.uuid4
import io.github.geniyyc.mathface.common.models.MfExpression
import io.github.geniyyc.mathface.common.models.MfExpressionId
import io.github.geniyyc.mathface.common.repo.DbExpressionFilterRequest
import io.github.geniyyc.mathface.common.repo.DbExpressionIdRequest
import io.github.geniyyc.mathface.common.repo.DbExpressionRequest
import io.github.geniyyc.mathface.common.repo.DbExpressionResponseOk
import io.github.geniyyc.mathface.common.repo.DbExpressionsResponseOk
import io.github.geniyyc.mathface.common.repo.ExpressionRepoBase
import io.github.geniyyc.mathface.common.repo.IDbExpressionResponse
import io.github.geniyyc.mathface.common.repo.IDbExpressionsResponse
import io.github.geniyyc.mathface.common.repo.IRepoExpression
import io.github.geniyyc.mathface.common.repo.errorEmptyId
import io.github.geniyyc.mathface.common.repo.errorNotFound
import io.github.geniyyc.mathface.repo.common.IRepoExpressionInitializable
import io.github.smyrgeorge.sqlx4k.ConnectionPool
import io.github.smyrgeorge.sqlx4k.Statement
import io.github.smyrgeorge.sqlx4k.postgres.postgreSQL
import kotlinx.coroutines.runBlocking

class RepoExpressionSql(
    properties: SqlProperties = SqlProperties(),
    private val randomUuid: () -> String = { uuid4().toString() },
) : ExpressionRepoBase(), IRepoExpression, IRepoExpressionInitializable {

    private val db by lazy {
        postgreSQL(
            url = properties.url,
            username = properties.user,
            password = properties.password,
            options = ConnectionPool.Options(maxConnections = properties.maxConnections),
        )
    }

    private val dbName = "\"${properties.schema}\".\"${properties.table}\""
    private val cols = SqlFields.allFields.joinToString { it.quoted() }

    override suspend fun createExpression(rq: DbExpressionRequest): IDbExpressionResponse = tryExpressionMethod {
        val expression = rq.expression.copy(id = MfExpressionId(randomUuid()))
        val stmt = Statement.create(SqlQueryBuilder.insert(dbName, cols)).bindExpression(expression)
        val rows: List<MfExpression> = db.fetchAll(stmt, MfExpressionRowMapper).getOrThrow()
        if (rows.isEmpty()) throw RuntimeException("DB error: insert returned no rows")
        DbExpressionResponseOk(rows.first())
    }

    override suspend fun readExpression(rq: DbExpressionIdRequest): IDbExpressionResponse = tryExpressionMethod {
        val id = rq.id.takeIf { it != MfExpressionId.NONE } ?: return@tryExpressionMethod errorEmptyId
        val stmt = Statement.create(SqlQueryBuilder.read(dbName, cols))
            .bind(SqlFields.ID, id.asString())
        val rows: List<MfExpression> = db.fetchAll(stmt, MfExpressionRowMapper).getOrThrow()
        if (rows.isEmpty()) errorNotFound(rq.id)
        else DbExpressionResponseOk(rows.first())
    }

    override suspend fun updateExpression(rq: DbExpressionRequest): IDbExpressionResponse = tryExpressionMethod {
        val expression = rq.expression
        val id = expression.id.takeIf { it != MfExpressionId.NONE } ?: return@tryExpressionMethod errorEmptyId
        val stmt = Statement.create(SqlQueryBuilder.update(dbName, cols)).bindExpression(expression)
        val rows: List<MfExpression> = db.fetchAll(stmt, MfExpressionRowMapper).getOrThrow()
        if (rows.isEmpty()) errorNotFound(id)
        else DbExpressionResponseOk(rows.first())
    }

    override suspend fun deleteExpression(rq: DbExpressionIdRequest): IDbExpressionResponse = tryExpressionMethod {
        val id = rq.id.takeIf { it != MfExpressionId.NONE } ?: return@tryExpressionMethod errorEmptyId
        val stmt = Statement.create(SqlQueryBuilder.delete(dbName, cols))
            .bind(SqlFields.ID, id.asString())
        val rows: List<MfExpression> = db.fetchAll(stmt, MfExpressionRowMapper).getOrThrow()
        if (rows.isEmpty()) errorNotFound(id)
        else DbExpressionResponseOk(rows.first())
    }

    override suspend fun searchExpression(rq: DbExpressionFilterRequest): IDbExpressionsResponse = tryExpressionsMethod {
        val stmt = Statement.create(SqlQueryBuilder.search(dbName, cols))
            .bind(SqlFields.COMPLEXITY_ID, rq.level)
        val rows: List<MfExpression> = db.fetchAll(stmt, MfExpressionRowMapper).getOrThrow()
        DbExpressionsResponseOk(data = rows)
    }

    override fun save(expressions: Collection<MfExpression>): Collection<MfExpression> = runBlocking {
        expressions.map { expression ->
            val stmt = Statement.create(SqlQueryBuilder.insert(dbName, cols)).bindExpression(expression)
            val rows: List<MfExpression> = db.fetchAll(stmt, MfExpressionRowMapper).getOrThrow()
            rows.first()
        }
    }

    fun clear(): Unit = runBlocking {
        db.execute(Statement.create(SqlQueryBuilder.clear(dbName))).getOrThrow()
    }
}
