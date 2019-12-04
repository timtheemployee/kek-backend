package kek.foundation.backend.attackype

import kek.foundation.backend.repositories.attacktype.AttackType
import kek.foundation.backend.repositories.attacktype.AttackTypeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AttackTypeController @Autowired constructor(
    private val attackTypeRepository: AttackTypeRepository
) {

    @GetMapping("/attack-types")
    fun getAttackTypes(): List<AttackType> =
        attackTypeRepository.get()
}