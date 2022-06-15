package io.github.tuguzt.pcbuilder.backend.data.di

import de.mkammerer.argon2.Argon2
import io.github.tuguzt.pcbuilder.backend.data.datasource.ManufacturerDataSource
import io.github.tuguzt.pcbuilder.backend.data.datasource.PasswordHashDataSource
import io.github.tuguzt.pcbuilder.backend.data.datasource.PasswordUserDataSource
import io.github.tuguzt.pcbuilder.backend.data.datasource.UserDataSource
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.impl.Argon2PasswordHashDataSource
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.impl.LocalManufacturerDataSource
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.impl.LocalPasswordUserDataSource
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.impl.LocalUserDataSource
import org.jetbrains.exposed.sql.Database
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf

internal fun Module.declareDataSources() {
    singleOf(::userDataSource)
    singleOf(::passwordUserDataSource)
    singleOf(::passwordHashDataSource)
    singleOf(::manufacturerDataSource)
}

private fun userDataSource(database: Database): UserDataSource =
    LocalUserDataSource(database)

private fun passwordUserDataSource(database: Database): PasswordUserDataSource =
    LocalPasswordUserDataSource(database)

private fun passwordHashDataSource(argon2: Argon2): PasswordHashDataSource =
    Argon2PasswordHashDataSource(argon2)

private fun manufacturerDataSource(database: Database): ManufacturerDataSource =
    LocalManufacturerDataSource(database)
