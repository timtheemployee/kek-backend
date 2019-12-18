package kek.foundation.backend.filters

const val MIN_YEAR = 1974
const val MAX_YEAR = 1996

data class Filter(
    val minYear: Int?,
    val maxYear: Int?,
    val isExtended: Boolean?,
    val countries: List<Int>?,
    val regions: List<Int>?,
    val isSuccess: Boolean?,
    val isSuicide: Boolean?,
    val attackTypes: List<Int>?,
    val targetTypes: List<Int>?,
    val groupsId: List<Int>?
)