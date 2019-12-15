package kek.foundation.backend.repositories

import kek.foundation.backend.database.Datasource
import kek.foundation.backend.repositories.attacktype.AttackTypeRepository
import kek.foundation.backend.repositories.attacktype.AttackTypeRepositoryImpl
import kek.foundation.backend.repositories.country.CountryRepository
import kek.foundation.backend.repositories.country.CountryRepositoryImpl
import kek.foundation.backend.repositories.filters.FiltersRepository
import kek.foundation.backend.repositories.filters.FiltersRepositoryImpl
import kek.foundation.backend.repositories.regions.RegionRepository
import kek.foundation.backend.repositories.regions.RegionRepositoryImpl
import kek.foundation.backend.repositories.targettype.TargetTypeRepository
import kek.foundation.backend.repositories.targettype.TargetTypeRepositoryImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RepositoriesConfiguration {

    @Bean
    fun provideCountryRepository(datasource: Datasource): CountryRepository =
        CountryRepositoryImpl(datasource)

    @Bean
    fun provideAttackTypeRepository(datasource: Datasource): AttackTypeRepository =
        AttackTypeRepositoryImpl(datasource)

    @Bean
    fun provideRegionsRepository(datasource: Datasource): RegionRepository =
        RegionRepositoryImpl(datasource)

    @Bean
    fun provideFilterRepository(datasource: Datasource): FiltersRepository =
        FiltersRepositoryImpl(datasource)

    @Bean
    fun provideTargetTypeRepository(datasource: Datasource): TargetTypeRepository =
        TargetTypeRepositoryImpl(datasource)
}