package kek.foundation.backend.repositories.tracing

data class TraceRecord(
    val traceEventId: String,
    val atTime: String,
    val endpointHash: String,
    val trace: Trace
)