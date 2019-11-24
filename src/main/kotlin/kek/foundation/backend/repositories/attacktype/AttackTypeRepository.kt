package kek.foundation.backend.repositories.attacktype

import kek.foundation.backend.database.Datasource
import org.springframework.beans.factory.annotation.Autowired

interface AttackTypeRepository {
    fun get(): List<AttackType>
}

class AttackTypeRepositoryImpl @Autowired constructor(
        private val datasource: Datasource
): AttackTypeRepository {

    private companion object {
        const val ATTACK_TYPE_ID = "attacktype"
        const val ATTACK_TYPE_DESCRIPTION = "attacktypedescription"
    }

    override fun get(): List<AttackType> {
        val result = datasource.query("select * from attack_types")

        val countries = arrayListOf<AttackType>()

        while (result.next()) {
            countries.add(
                    AttackType(
                            result.getLong(ATTACK_TYPE_ID),
                            result.getString(ATTACK_TYPE_DESCRIPTION)
                    )
            )
        }

        return countries
    }
}