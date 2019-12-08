package kek.foundation.backend.filters

import kek.foundation.backend.repositories.filters.FiltersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class FiltersController @Autowired constructor(
    private val repository: FiltersRepository
) {

    @PostMapping("/filterBy")
    fun findBy(@RequestBody filter: Filter): List<Event> =
        repository.findBy(filter)
}