package kek.foundation.backend.groups

import kek.foundation.backend.repositories.groups.GroupsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GroupsController @Autowired constructor(
    private val groupsRepository: GroupsRepository
) {

    @GetMapping("/groups")
    fun get(): List<Group> =
        groupsRepository.get()
}