package kek.foundation.backend.repositories.tracing

data class Trace(
    val deviceName: String,
    val endpoint: String,
    val statusCode: Int
)