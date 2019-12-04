package kek.foundation.backend.database

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

class KekDatabase : Datasource {

    companion object {

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
}