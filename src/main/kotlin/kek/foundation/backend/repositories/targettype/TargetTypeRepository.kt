package kek.foundation.backend.repositories.targettype

import kek.foundation.backend.database.Datasource
import org.springframework.beans.factory.annotation.Autowired

interface TargetTypeRepository {

    fun get(): List<TargetType>
}

class TargetTypeRepositoryImpl @Autowired constructor(
    private val datasource: Datasource
): TargetTypeRepository {

    private companion object {
        const val ID_COLUMN = "id"
        const val NAME_COLUMN = "target_type"
    }

    override fun get(): List<TargetType> =
        with(datasource.query("select * from target_types")) {
            val targetTypes = arrayListOf<TargetType>()

            while (next()) {
                targetTypes.add(
                    TargetType(
                        getInt(ID_COLUMN),
                        getString(NAME_COLUMN)
                    )
                )
            }

            return targetTypes
        }
}