package kek.foundation.backend.countries

import kek.foundation.backend.repositories.country.Country
import kek.foundation.backend.repositories.country.CountryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CountryFilterController @Autowired constructor (
        private val countryRepository: CountryRepository
) {

    @GetMapping("/countries")
    fun greeting(): List<Country> =
            countryRepository.get()

}