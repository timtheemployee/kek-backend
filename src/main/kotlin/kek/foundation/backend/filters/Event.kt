package kek.foundation.backend.filters

data class Event(
    val id: String,
    val year: String,
    val month: String,
    val day: String,
    val latitude: String,
    val longitude: String,
    val summary: String
)