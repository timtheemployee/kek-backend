package kek.foundation.backend.tracing

import kek.foundation.backend.repositories.tracing.TraceRecord
import kek.foundation.backend.repositories.tracing.TracingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GetTracingController @Autowired constructor(
    private val tracingRepository: TracingRepository
) {

    @GetMapping("/traceRecords")
    fun getTracing(): List<TraceRecord> =
        tracingRepository.get()
}