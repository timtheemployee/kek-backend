package kek.foundation.backend.regions

import kek.foundation.backend.repositories.regions.Region
import kek.foundation.backend.repositories.regions.RegionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RegionController @Autowired constructor(
        private val regionRepository: RegionRepository
) {

    @GetMapping("/regions")
    fun getRegions(): List<Region> =
            regionRepository.get()

}