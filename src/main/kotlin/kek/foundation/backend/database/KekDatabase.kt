package kek.foundation.backend.database

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

class KekDatabase : Datasource {

    companion object {
        const val PATH = "jdbc:postgresql://localhost:5432/kek_database"
    }

    private val connection: Connection

    init {
        connection = connect()
    }

    private fun connect(): Connection {
        val dbUrl = System.getenv("JDBC_DATABASE_URL")

        return DriverManager.getConnection(dbUrl)
    }

    override fun query(queryText: String): ResultSet {
        val statement = connection.createStatement()

        return statement.executeQuery(queryText)
    }

    override fun execute(query: String): Boolean {
        val statement = connection.createStatement()

        return statement.execute(query)
    }
}