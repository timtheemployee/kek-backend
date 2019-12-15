package kek.foundation.backend.filters

data class Event(
    val eventId: String,
    val year: Int,
    val month: Int,
    val day: Int,
    val extended: Int,
    val country: String,
    val region: String,
    val latitude: Double,
    val longitude: Double,
    val summary: String?,
    val isSuccess: Boolean,
    val isSuicide: Boolean,
    val attackType: String,
    val targetType: String,
    val killsCount: Int,
    val group: String
)