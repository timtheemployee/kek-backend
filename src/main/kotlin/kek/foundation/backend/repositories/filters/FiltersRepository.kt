package kek.foundation.backend.repositories.filters

import kek.foundation.backend.database.Datasource
import kek.foundation.backend.filters.Event
import kek.foundation.backend.filters.Filter
import org.springframework.beans.factory.annotation.Autowired

interface FiltersRepository {
    fun findBy(filter: Filter): List<Event>
}

class FiltersRepositoryImpl @Autowired constructor(
    private val datasource: Datasource
) : FiltersRepository {

    private companion object {
        const val EVENT_ID = "eventid"
        const val YEAR = "iyear"
        const val MONTH = "imonth"
        const val DAY = "iday"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
        const val SUMMARY = "summary"
    }

    override fun findBy(filter: Filter): List<Event> {
        val result = datasource.query(
            createQuery(filter.countryId, filter.attackTypeId, filter.regionId)
        )

        val events = arrayListOf<Event>()

        while (result.next()) {
            events.add(
                Event(
                    id = result.getString(EVENT_ID),
                    year = result.getString(YEAR),
                    month = result.getString(MONTH),
                    day = result.getString(DAY),
                    latitude = result.getString(LATITUDE),
                    longitude = result.getString(LONGITUDE),
                    summary = result.getString(SUMMARY)
                )
            )
        }

        return events
    }

    private fun createQuery(countryId: String?, attackTypeId: String?, regionId: String?) =
        "select $EVENT_ID, $YEAR, $MONTH, $DAY, $LATITUDE, $LONGITUDE, $SUMMARY " +
                "from global_table where country=${countryId ?: ""}, " +
                "attacktype1=${attackTypeId ?: ""}, region=${regionId ?: ""};"
}