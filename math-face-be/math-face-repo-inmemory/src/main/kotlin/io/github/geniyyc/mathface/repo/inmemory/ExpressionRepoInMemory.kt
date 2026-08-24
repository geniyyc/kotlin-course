package io.github.geniyyc.mathface.repo.inmemory

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
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class ExpressionRepoInMemory(
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
) : ExpressionRepoBase(), IRepoExpression, IRepoExpressionInitializable {

    private val mutex: Mutex = Mutex()
    private val cache = Cache.Builder<String, ExpressionEntity>()
        .expireAfterWrite(ttl)
        .build()

    override fun save(expressions: Collection<MfExpression>) = expressions.map { expression ->
        val entity = ExpressionEntity(expression)
        require(entity.id != null)
        cache.put(entity.id, entity)
        expression
    }

    override suspend fun createExpression(rq: DbExpressionRequest): IDbExpressionResponse = tryExpressionMethod {
        val key = randomUuid()
        val expression = rq.expression.copy(id = MfExpressionId(key))
        val entity = ExpressionEntity(expression)
        mutex.withLock {
            cache.put(key, entity)
        }
        DbExpressionResponseOk(expression)
    }

    override suspend fun readExpression(rq: DbExpressionIdRequest): IDbExpressionResponse = tryExpressionMethod {
        val key = rq.id.takeIf { it != MfExpressionId.NONE }?.asString() ?: return@tryExpressionMethod errorEmptyId
        mutex.withLock {
            cache.get(key)
                ?.let { DbExpressionResponseOk(it.toInternal()) }
                ?: errorNotFound(rq.id)
        }
    }

    override suspend fun updateExpression(rq: DbExpressionRequest): IDbExpressionResponse = tryExpressionMethod {
        val rqExpression = rq.expression
        val id = rqExpression.id.takeIf { it != MfExpressionId.NONE } ?: return@tryExpressionMethod errorEmptyId
        val key = id.asString()

        mutex.withLock {
            val oldExpression = cache.get(key)?.toInternal()
            when {
                oldExpression == null -> errorNotFound(id)
                else -> {
                    val newExpression = rqExpression.copy()
                    val entity = ExpressionEntity(newExpression)
                    cache.put(key, entity)
                    DbExpressionResponseOk(newExpression)
                }
            }
        }
    }

    override suspend fun deleteExpression(rq: DbExpressionIdRequest): IDbExpressionResponse = tryExpressionMethod {
        val id = rq.id.takeIf { it != MfExpressionId.NONE } ?: return@tryExpressionMethod errorEmptyId
        val key = id.asString()

        mutex.withLock {
            val oldExpression = cache.get(key)?.toInternal()
            when {
                oldExpression == null -> errorNotFound(id)
                else -> {
                    cache.invalidate(key)
                    DbExpressionResponseOk(oldExpression)
                }
            }
        }
    }

    override suspend fun searchExpression(rq: DbExpressionFilterRequest): IDbExpressionsResponse = tryExpressionsMethod {
        val result = cache.asMap().asSequence()
            .filter { entry ->
                rq.level.takeIf { it != 0 }?.let {
                    it == entry.value.complexityId
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()
        DbExpressionsResponseOk(result)
    }
}
