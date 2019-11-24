package kek.foundation.backend.database

import java.sql.ResultSet

interface Datasource {

    fun query(queryText: String): ResultSet
}