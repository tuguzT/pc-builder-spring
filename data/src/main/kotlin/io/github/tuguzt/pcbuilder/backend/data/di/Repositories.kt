package io.github.tuguzt.pcbuilder.backend.data.di

import io.github.tuguzt.pcbuilder.backend.data.datasource.ManufacturerDataSource
import io.github.tuguzt.pcbuilder.backend.data.datasource.PasswordHashDataSource
import io.github.tuguzt.pcbuilder.backend.data.datasource.PasswordUserDataSource
import io.github.tuguzt.pcbuilder.backend.data.datasource.UserDataSource
import io.github.tuguzt.pcbuilder.backend.data.repository.PasswordHashRepository
import io.github.tuguzt.pcbuilder.backend.data.repository.PasswordUserRepository
import io.github.tuguzt.pcbuilder.backend.data.repository.impl.ManufacturerRepositoryImpl
import io.github.tuguzt.pcbuilder.backend.data.repository.impl.PasswordHashRepositoryImpl
import io.github.tuguzt.pcbuilder.backend.data.repository.impl.PasswordUserRepositoryImpl
import io.github.tuguzt.pcbuilder.backend.data.repository.impl.UserRepositoryImpl
import io.github.tuguzt.pcbuilder.domain.repository.component.ManufacturerRepository
import io.github.tuguzt.pcbuilder.domain.repository.user.UserRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf

internal fun Module.declareRepositories() {
    singleOf(::userRepository)
    singleOf(::passwordUserRepository)
    singleOf(::passwordHashRepository)
    singleOf(::manufacturerRepository)
}

private fun userRepository(dataSource: UserDataSource): UserRepository<Nothing?> =
    UserRepositoryImpl(dataSource)

private fun passwordUserRepository(dataSource: PasswordUserDataSource): PasswordUserRepository<Nothing?> =
    PasswordUserRepositoryImpl(dataSource)

private fun passwordHashRepository(dataSource: PasswordHashDataSource): PasswordHashRepository =
    PasswordHashRepositoryImpl(dataSource)

private fun manufacturerRepository(dataSource: ManufacturerDataSource): ManufacturerRepository<Nothing?> =
    ManufacturerRepositoryImpl(dataSource)
