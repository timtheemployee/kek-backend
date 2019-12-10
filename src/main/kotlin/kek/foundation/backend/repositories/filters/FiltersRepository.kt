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
        const val REGION = "region"
        const val SUMMARY = "summary"
        const val SUCCESS = "success"
        const val SUICIDE = "suicide"
        const val ATTACK_TYPE = "attacktype1"
        const val TARGET_TYPE = "targtype1"
        const val GROUP_ID = "groupid"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
        const val KILLS_COUNT = "nkill"


        const val GLOBAL_TABLE = "global"
        val GLOBAL_COLUMNS = listOf(EVENT_ID, YEAR, MONTH, DAY, LATITUDE, LONGITUDE, SUMMARY, TARGET_TYPE, KILLS_COUNT)
    }

    private val countriesTable = Triple("countries", "countrycode", "countryname")
    private val attackTypesTable = Triple("attack_types", "attacktype", "attacktypedescription")
    private val groupsTable = Triple("groups", "id", "group_name")
    private val regionsTable = Triple("regions", "regioncode", "regionname")

    override fun findBy(filter: Filter): List<Event> {
        val result = datasource.query(
            with(filter) {
                createQuery(
                    maxYear = maxYear.toString(),
                    minYear = minYear.toString(),
                    isExtended = joinToString(isExtended),
                    countries = joinToString(countries),
                    regions = joinToString(regions),
                    isSuccess = joinToString(isSuccess),
                    isSuicide = joinToString(isSuicide),
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
                    region = result.getInt(REGION),
                    latitude = result.getDouble(LATITUDE),
                    longitude = result.getDouble(LONGITUDE),
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

    private fun joinToString(list: List<Int>?): String? =
        list?.joinToString(", ")

    private fun joinToString(flag: Boolean?): String? =
        if(flag != null) {
            if(flag) {
                "1"
            } else {
                "0"
            }
        } else {
            null
        }


    /**
     * global.country = countries.countrycode -> countries.countryname
     * global.region - regions.regioncode -> regions.regionname
     * global.attacktype1 - attack_types.attacktype -> attack_types.attacktypedescription
     * global.groupid - groups.id -> groups.group_name
     */
    private fun createQuery(
        maxYear: String,
        minYear: String,
        isExtended: String?,
        countries: String?,
        regions: String?,
        isSuccess: String?,
        isSuicide: String?,
        attackTypes: String?,
        targetTypes: String?,
        groupsId: String?
    ): String {

        //TODO("Add correct query params")
        val globalParams = GLOBAL_COLUMNS.map { "$GLOBAL_TABLE.$it" }
        val countryParams = this.countriesTable.firstAndLast()
        val attackTypeParams = this.attackTypesTable.firstAndLast()
        val groupParams = this.groupsTable.firstAndLast()
        val regionsParams = this.regionsTable.firstAndLast()

        val allParams = arrayListOf<String>().apply {
            addAll(globalParams)
            add(countryParams)
            add(attackTypeParams)
            add(groupParams)
            add(regionsParams)
        }.joinToString(separator = ", ")

        val conditions = arrayListOf<String>().apply {
            add(onCondition(maxYear.isNotEmpty() && minYear.isNotEmpty(), doReturn = "($YEAR >= $minYear and $YEAR <= $maxYear)"))
            add(onCondition(isExtended?.isNotEmpty(), doReturn = "($EXTENDED = $isExtended)"))
            add(onCondition(countries?.isNotEmpty(), doReturn = "($EXTENDED = $isExtended)"))
            add(onCondition(regions?.isNotEmpty(), doReturn = "($REGION in ($regions))"))
            add(onCondition(isSuccess?.isNotEmpty(), doReturn = "($SUCCESS = $isSuccess)"))
            add(onCondition(isSuicide?.isNotEmpty(), doReturn = "($SUICIDE = $isSuicide)"))
            add(onCondition(attackTypes?.isNotEmpty(), doReturn = "($ATTACK_TYPE in ($attackTypes))"))
            add(onCondition(targetTypes?.isNotEmpty(), doReturn = "($TARGET_TYPE in ($targetTypes))"))
            add(onCondition(groupsId?.isNotEmpty(), doReturn = "($GROUP_ID in ($groupsId)"))
        }

        val joinConditions = arrayListOf<String>().apply {
            add(join(countriesTable, "$GLOBAL_TABLE.$COUNTRY"))
            add(join(attackTypesTable, "$GLOBAL_TABLE.$ATTACK_TYPE"))
            add(join(groupsTable, "$GLOBAL_TABLE.$GROUP_ID"))
            add(join(regionsTable, "$GLOBAL_TABLE.$REGION"))
        }.joinToString("\n")

        return "select $allParams from global \n$joinConditions\n where ${conditions.filter { it.isNotEmpty() }.joinToString(" and")}".apply { println("FILTER QUERY -> $this") }
    }

    private fun join(params: Triple<String, String, String>, target: String) =
        "inner join ${params.first} on ${params.firstAndSecond()} = $target "

    private fun onCondition(statement: Boolean?, doReturn: String): String =
        if (statement != null && statement) doReturn else ""

    private fun Triple<String, String, String>.firstAndLast(): String =
        "$first.$third"

    private fun Triple<String, String, String>.firstAndSecond(): String =
        "$first.$second"
}