package kek.foundation.backend.repositories.regions

import kek.foundation.backend.database.Datasource
import org.springframework.beans.factory.annotation.Autowired

interface RegionRepository {
    fun get(): List<Region>
}

class RegionRepositoryImpl @Autowired constructor(
    private val datasource: Datasource
): RegionRepository {

    private companion object {
        const val REGION_ID = "regioncode"
        const val REGION_NAME = "regionname"
    }

    override fun get(): List<Region> {
        val resultSet = datasource.query("select * from regions")
        val regions = arrayListOf<Region>()

        while (resultSet.next()) {
            regions.add(
                    Region(
                            resultSet.getLong(REGION_ID),
                            resultSet.getString(REGION_NAME)
                    )
            )
        }

        return regions
    }
}