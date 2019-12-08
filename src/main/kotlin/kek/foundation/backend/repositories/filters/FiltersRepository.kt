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
        const val EXTENDED = "extended"
        const val COUNTRY = "country"
        const val REGIONS = "regions"
        const val SUMMARY = "summary"
        const val SUCCESS = "success"
        const val SUICIDE = "suicide"
        const val ATTACK_TYPE = "attacktype1"
        const val TARGET_TYPE = "targettype1"
        const val GROUP_ID = "groupid"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
        const val KILLS_COUNT = "nkills"
    }

    override fun findBy(filter: Filter): List<Event> {
        val result = datasource.query(
            with(filter) {
                createQuery(
                    maxYear = maxYear.toString(),
                    minYear = minYear.toString(),
                    isExtended = joinToString(isExtended),
                    countries = joinToString(countries),
                    regions = joinToString(regions),
                    isSuccess = isSuccess.toString(),
                    isSuicide = isSuicide.toString(),
                    attackTypes = joinToString(attackTypes),
                    targetTypes = joinToString(targetTypes),
                    groupsId = joinToString(groupsId)
                )
            }
        )

        val events = arrayListOf<Event>()

        while (result.next()) {
            events.add(
                Event(
                    eventId = result.getString(EVENT_ID),
                    year = result.getInt(YEAR),
                    month = result.getInt(MONTH),
                    day = result.getInt(DAY),
                    extended = result.getInt(EXTENDED),
                    country = result.getInt(COUNTRY),
                    region = result.getInt(REGIONS),
                    latitude = result.getString(LATITUDE),
                    longitude = result.getString(LONGITUDE),
                    summary = result.getString(SUMMARY),
                    isSuccess = result.getBoolean(SUCCESS),
                    isSuicide = result.getBoolean(SUICIDE),
                    attackType = result.getInt(ATTACK_TYPE),
                    targetType = result.getInt(TARGET_TYPE),
                    killsCount = result.getInt(KILLS_COUNT),
                    group = result.getInt(GROUP_ID)
                )
            )
        }

        return events
    }

    private fun joinToString(list: List<Int>): String =
        list.joinToString(", ")

    private fun joinToString(bool: Boolean): String =
        if (bool) "1" else "0"

    private fun createQuery(
        maxYear: String,
        minYear: String,
        isExtended: String,
        countries: String,
        regions: String,
        isSuccess: String,
        isSuicide: String,
        attackTypes: String,
        targetTypes: String,
        groupsId: String
    ): String =
        """
            select * from global where 
            ($YEAR > $minYear and $YEAR < $maxYear) and 
            ($EXTENDED = $isExtended) and
            ${onCondition(countries.isNotEmpty(), doReturn = "($EXTENDED = $isExtended) and")}
            ${onCondition(regions.isNotEmpty(), doReturn = "($REGIONS in ($regions)) and")} 
            ($SUCCESS = $isSuccess) and
            ($SUICIDE = $isSuicide) and
            ${onCondition(attackTypes.isNotEmpty(), doReturn = "($ATTACK_TYPE in ($attackTypes)) and")}
            ${onCondition(targetTypes.isNotEmpty(), doReturn = "($TARGET_TYPE in ($targetTypes)) and")}
            ${onCondition(groupsId.isNotEmpty(), doReturn = "($GROUP_ID in ($groupsId)")})
            
        """.trimIndent().apply {
            println("FILTER QUERY -> $this")
        }

    private fun onCondition(statement: Boolean, doReturn: String): String =
        if (statement) doReturn else ""
}