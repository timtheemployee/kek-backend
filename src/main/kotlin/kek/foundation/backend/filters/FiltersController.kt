package kek.foundation.backend.filters

import kek.foundation.backend.repositories.filters.FiltersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
//TODO(REST CONTROLLER)
class FiltersController @Autowired constructor(
    private val repository: FiltersRepository
) {

    //TODO(POST MAPPING)
    fun findBy(@RequestBody filter: Filter): List<Event> =
        repository.findBy(filter)
}