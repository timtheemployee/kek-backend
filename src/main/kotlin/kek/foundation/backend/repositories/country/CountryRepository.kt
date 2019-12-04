package kek.foundation.backend.repositories.country

import kek.foundation.backend.database.Datasource
import org.springframework.beans.factory.annotation.Autowired

interface CountryRepository {
    fun get(): List<Country>
}

class CountryRepositoryImpl @Autowired constructor(
    private val datasource: Datasource
) : CountryRepository {

    private companion object {
        const val COUNTRY_ID = "countrycode"
        const val COUNTRY_NAME = "countryname"
    }

    override fun get(): List<Country> {
        val resultSet = datasource.query("select * from countries")
        val countries = arrayListOf<Country>()

        while (resultSet.next()) {
            countries.add(
                Country(
                    resultSet.getLong(COUNTRY_ID),
                    resultSet.getString(COUNTRY_NAME)
                )
            )
        }

        return countries
    }
}