package kek.foundation.backend.filters

data class Event(
    val eventId: String,
    val year: Int,
    val month: Int,
    val day: Int,
    val extended: Int, //TODO( String from is_extended table )
    val country: Int, //TODO ( String from countries table )
    val region: Int, //TODO ( String from region table )
    val latitude: Double,
    val longitude: Double,
    val summary: String?,
    val isSuccess: Boolean,
    val isSuicide: Boolean,
    val attackType: Int, // TODO ( String from attack_types table )
    val targetType: Int,// TODO ( String from target_type table )
    val killsCount: Int,
    val group: Int // TODO ( String from groups table )
)