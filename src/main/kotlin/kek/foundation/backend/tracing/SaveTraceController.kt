package kek.foundation.backend.tracing

import kek.foundation.backend.repositories.tracing.Trace
import kek.foundation.backend.repositories.tracing.TracingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SaveTraceController @Autowired constructor(
    private val tracingRepository: TracingRepository
) {

    @PostMapping("/traceEvent")
    fun traceEvent(@RequestBody trace: Trace) {
        tracingRepository.add(trace)
    }
}