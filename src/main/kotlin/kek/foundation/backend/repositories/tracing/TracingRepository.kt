package kek.foundation.backend.repositories.tracing

import kek.foundation.backend.database.Datasource
import org.springframework.beans.factory.annotation.Autowired

interface TracingRepository {

    fun add(trace: Trace)

    fun get(): List<TraceRecord>
}

class TracingRepositoryImpl @Autowired constructor(
    private val datasource: Datasource
): TracingRepository {

    private companion object {
        const val EVENT_ID = "event_id"
        const val DEVICE_NAME = "device_name"
        const val REQUEST_CODE = "request_code"
        const val STATUS_CODE = "status_code"
        const val AT_TIME = "at_time"
        const val REQUEST_ENDPOINT = "request_endpoint"
    }

    override fun add(trace: Trace) {
        runCatching {
            datasource
                .execute("select add_new_trace_event('${trace.deviceName.replace(" ", "")}', '${trace.endpoint}', ${trace.statusCode})")
        }
            .onSuccess { added -> if (added)
                println("ADDED NEW TRACE EVENT WITH ${trace.deviceName}, ${trace.endpoint}, ${trace.statusCode}")
                else
                println("CANNOT ADD NEW TRACE EVENT WITH ${trace.deviceName}, ${trace.endpoint}, ${trace.statusCode}")
            }
            .onFailure {
                println("CANNOT ADD NEW TRACE EVENT CAUSE ${it.printStackTrace()}")
            }
    }

    override fun get(): List<TraceRecord> {
        val query = """
            select tracing.event_id, tracing.device_name, tracing.request_code, tracing.status_code, tracing.at_time,
            request_codes.request_endpoint
            from tracing
            inner join request_codes on request_codes.request_code = tracing.request_code
        """.trimIndent()

        return with(datasource.query(query)) {
            val traceRecords = arrayListOf<TraceRecord>()

            while (next()) {
                traceRecords.add(
                    TraceRecord(
                        getString(EVENT_ID),
                        getDate(AT_TIME).toString(),
                        getString(REQUEST_CODE),
                        Trace(
                            getString(DEVICE_NAME),
                            getString(REQUEST_ENDPOINT),
                            getInt(STATUS_CODE)
                        )
                    )
                )
            }

            traceRecords
        }
    }
}