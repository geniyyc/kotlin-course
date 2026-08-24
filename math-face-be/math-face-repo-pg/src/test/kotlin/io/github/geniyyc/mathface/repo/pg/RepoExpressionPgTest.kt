package io.github.geniyyc.mathface.repo.pg

import com.benasher44.uuid.uuid4
import io.github.geniyyc.mathface.repo.common.ExpressionRepoInitialized
import io.github.geniyyc.mathface.repo.tests.RepoExpressionCreateTest
import io.github.geniyyc.mathface.repo.tests.RepoExpressionDeleteTest
import io.github.geniyyc.mathface.repo.tests.RepoExpressionReadTest
import io.github.geniyyc.mathface.repo.tests.RepoExpressionSearchTest
import io.github.geniyyc.mathface.repo.tests.RepoExpressionUpdateTest

private fun createClearedRepo(randomUuid: () -> String = { uuid4().toString() }): ExpressionRepoInitialized {
    PostgresContainer.assumeJavaVersion()
    val sqlRepo = RepoExpressionSql(PostgresContainer.sqlProperties(), randomUuid = randomUuid)
    sqlRepo.clear()
    return ExpressionRepoInitialized(sqlRepo, initObjects = emptyList())
}

class RepoExpressionPgCreateTest : RepoExpressionCreateTest() {
    override val repo = createClearedRepo(randomUuid = { uuidNew.asString() })
}

class RepoExpressionPgDeleteTest : RepoExpressionDeleteTest() {
    override val repo = createClearedRepo().apply {
        save(initObjects)
    }
}

class RepoExpressionPgReadTest : RepoExpressionReadTest() {
    override val repo = createClearedRepo().apply {
        save(initObjects)
    }
}

class RepoExpressionPgSearchTest : RepoExpressionSearchTest() {
    override val repo = createClearedRepo().apply {
        save(initObjects)
    }
}

class RepoExpressionPgUpdateTest : RepoExpressionUpdateTest() {
    override val repo = createClearedRepo().apply {
        save(initObjects)
    }
}
