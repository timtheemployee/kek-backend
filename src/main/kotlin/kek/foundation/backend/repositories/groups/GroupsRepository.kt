package kek.foundation.backend.repositories.groups

import kek.foundation.backend.database.Datasource
import kek.foundation.backend.groups.Group
import org.springframework.beans.factory.annotation.Autowired

interface GroupsRepository {

    fun get(): List<Group>
}

class GroupsRepositoryImpl @Autowired constructor(
    private val datasource: Datasource
) : GroupsRepository {

    private companion object {
        const val ID_COLUMN = "id"
        const val NAME_COLUMN = "group_name"
    }

    override fun get(): List<Group> =
        with(datasource.query("select * from groups")) {
            val groups = arrayListOf<Group>()

            while (next()) {
                groups.add(
                    Group(
                        getInt(ID_COLUMN),
                        getString(NAME_COLUMN)
                    )
                )
            }

            return groups
        }
}