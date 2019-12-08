package kek.foundation.backend.filters

private const val MIN_YEAR = 1974
private const val MAX_YEAR = 1996
private const val NOT_EXTENDED = false

data class Filter(
    val minYear: Int = MIN_YEAR,
    val maxYear: Int = MAX_YEAR,
    val isExtended: Boolean = NOT_EXTENDED,
    val countries: List<Int> = emptyList(),
    val regions: List<Int> = emptyList(),
    val isSuccess: Boolean = false,
    val isSuicide: Boolean = false,
    val attackTypes: List<Int> = emptyList(),
    val targetTypes: List<Int> = emptyList(),
    val groupsId: List<Int> = emptyList()
)