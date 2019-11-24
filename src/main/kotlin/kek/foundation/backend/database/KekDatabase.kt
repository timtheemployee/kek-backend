package kek.foundation.backend.database

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

class KekDatabase: Datasource {

    companion object {
        const val PATH = "jdbc:postgresql://localhost:5432/kek_database"
    }
    private val connection: Connection

    init {
        connection = DriverManager.getConnection(PATH)
    }

    override fun query(queryText: String): ResultSet {
        val statement = connection.createStatement()

        return statement.executeQuery(queryText)
    }
}