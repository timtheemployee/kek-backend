package kek.foundation.backend.targettype

import kek.foundation.backend.repositories.targettype.TargetType
import kek.foundation.backend.repositories.targettype.TargetTypeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TargetTypeController @Autowired constructor(
    private val repository: TargetTypeRepository
) {
    @GetMapping("/target-types")
    fun get(): List<TargetType> =
        repository.get()
}