package io.github.tuguzt.pcbuilder.backend.data.di

import io.github.tuguzt.pcbuilder.backend.data.datasource.*
import io.github.tuguzt.pcbuilder.backend.data.repository.PasswordHashRepository
import io.github.tuguzt.pcbuilder.backend.data.repository.PasswordUserRepository
import io.github.tuguzt.pcbuilder.backend.data.repository.impl.*
import io.github.tuguzt.pcbuilder.domain.repository.component.ComponentRepository
import io.github.tuguzt.pcbuilder.domain.repository.component.ManufacturerRepository
import io.github.tuguzt.pcbuilder.domain.repository.user.UserRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf

internal fun Module.declareRepositories() {
    singleOf(::userRepository)
    singleOf(::passwordUserRepository)
    singleOf(::passwordHashRepository)
    singleOf(::manufacturerRepository)
    singleOf(::componentRepository)
}

private fun userRepository(dataSource: UserDataSource): UserRepository<Nothing?> =
    UserRepositoryImpl(dataSource)

private fun passwordUserRepository(dataSource: PasswordUserDataSource): PasswordUserRepository<Nothing?> =
    PasswordUserRepositoryImpl(dataSource)

private fun passwordHashRepository(dataSource: PasswordHashDataSource): PasswordHashRepository =
    PasswordHashRepositoryImpl(dataSource)

private fun manufacturerRepository(dataSource: ManufacturerDataSource): ManufacturerRepository<Nothing?> =
    ManufacturerRepositoryImpl(dataSource)

private fun componentRepository(dataSource: ComponentDataSource): ComponentRepository<Nothing?> =
    ComponentRepositoryImpl(dataSource)
