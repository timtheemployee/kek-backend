package kek.foundation.backend.filters

private const val MIN_YEAR = 1974
private const val MAX_YEAR = 1996
private const val NOT_EXTENDED = false

data class Filter(
    val minYear: Int = MIN_YEAR,
    val maxYear: Int = MAX_YEAR,
    val isExtended: Boolean = NOT_EXTENDED,
    val countries: List<Int>?,
    val regions: List<Int>?,
    val isSuccess: Boolean?,
    val isSuicide: Boolean?,
    val attackTypes: List<Int>?,
    val targetTypes: List<Int>?,
    val groupsId: List<Int>?
)