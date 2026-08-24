package io.github.geniyyc.mathface.repo.inmemory

import io.github.geniyyc.mathface.repo.common.ExpressionRepoInitialized
import io.github.geniyyc.mathface.repo.tests.RepoExpressionCreateTest
import io.github.geniyyc.mathface.repo.tests.RepoExpressionDeleteTest
import io.github.geniyyc.mathface.repo.tests.RepoExpressionReadTest
import io.github.geniyyc.mathface.repo.tests.RepoExpressionSearchTest
import io.github.geniyyc.mathface.repo.tests.RepoExpressionUpdateTest

class ExpressionRepoInMemoryCreateTest : RepoExpressionCreateTest() {
    override val repo = ExpressionRepoInitialized(
        ExpressionRepoInMemory(randomUuid = { uuidNew.asString() }),
        initObjects = initObjects,
    )
}

class ExpressionRepoInMemoryDeleteTest : RepoExpressionDeleteTest() {
    override val repo = ExpressionRepoInitialized(
        ExpressionRepoInMemory(),
        initObjects = initObjects,
    )
}

class ExpressionRepoInMemoryReadTest : RepoExpressionReadTest() {
    override val repo = ExpressionRepoInitialized(
        ExpressionRepoInMemory(),
        initObjects = initObjects,
    )
}

class ExpressionRepoInMemorySearchTest : RepoExpressionSearchTest() {
    override val repo = ExpressionRepoInitialized(
        ExpressionRepoInMemory(),
        initObjects = initObjects,
    )
}

class ExpressionRepoInMemoryUpdateTest : RepoExpressionUpdateTest() {
    override val repo = ExpressionRepoInitialized(
        ExpressionRepoInMemory(),
        initObjects = initObjects,
    )
}
