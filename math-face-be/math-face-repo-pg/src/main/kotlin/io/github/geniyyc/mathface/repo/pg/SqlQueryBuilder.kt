package io.github.geniyyc.mathface.repo.pg

object SqlQueryBuilder {

    fun insert(dbName: String, cols: String): String = """
        INSERT INTO $dbName (
          ${SqlFields.ID.quoted()},
          ${SqlFields.VALUE.quoted()},
          ${SqlFields.COMPLEXITY_ID.quoted()},
          ${SqlFields.DESCRIPTION.quoted()},
          ${SqlFields.OWNER_ID.quoted()},
          ${SqlFields.LOCK.quoted()}
        ) VALUES (
          :${SqlFields.ID},
          :${SqlFields.VALUE},
          :${SqlFields.COMPLEXITY_ID},
          :${SqlFields.DESCRIPTION},
          :${SqlFields.OWNER_ID},
          :${SqlFields.LOCK}
        )
        RETURNING $cols
        """.trimIndent()

    fun read(dbName: String, cols: String): String = """
        SELECT $cols
        FROM $dbName
        WHERE ${SqlFields.ID.quoted()} = :${SqlFields.ID}
        """.trimIndent()

    fun update(dbName: String, cols: String): String = """
        UPDATE $dbName
        SET ${SqlFields.VALUE.quoted()} = :${SqlFields.VALUE}
          , ${SqlFields.COMPLEXITY_ID.quoted()} = :${SqlFields.COMPLEXITY_ID}
          , ${SqlFields.DESCRIPTION.quoted()} = :${SqlFields.DESCRIPTION}
          , ${SqlFields.OWNER_ID.quoted()} = :${SqlFields.OWNER_ID}
          , ${SqlFields.LOCK.quoted()} = :${SqlFields.LOCK}
        WHERE ${SqlFields.ID.quoted()} = :${SqlFields.ID}
        RETURNING $cols
        """.trimIndent()

    fun delete(dbName: String, cols: String): String = """
        DELETE FROM $dbName
        WHERE ${SqlFields.ID.quoted()} = :${SqlFields.ID}
        RETURNING $cols
        """.trimIndent()

    fun search(dbName: String, cols: String): String = """
        SELECT $cols
        FROM $dbName
        WHERE ${SqlFields.COMPLEXITY_ID.quoted()} = :${SqlFields.COMPLEXITY_ID}
        """.trimIndent()

    fun clear(dbName: String): String = "DELETE FROM $dbName;"
}
