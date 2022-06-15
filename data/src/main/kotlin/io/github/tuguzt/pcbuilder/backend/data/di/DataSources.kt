package io.github.tuguzt.pcbuilder.backend.data.di

import de.mkammerer.argon2.Argon2
import io.github.tuguzt.pcbuilder.backend.data.datasource.*
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.impl.*
import org.jetbrains.exposed.sql.Database
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf

internal fun Module.declareDataSources() {
    singleOf(::userDataSource)
    singleOf(::passwordUserDataSource)
    singleOf(::passwordHashDataSource)
    singleOf(::manufacturerDataSource)
    singleOf(::componentDataSource)
}

private fun userDataSource(database: Database): UserDataSource =
    LocalUserDataSource(database)

private fun passwordUserDataSource(database: Database): PasswordUserDataSource =
    LocalPasswordUserDataSource(database)

private fun passwordHashDataSource(argon2: Argon2): PasswordHashDataSource =
    Argon2PasswordHashDataSource(argon2)

private fun manufacturerDataSource(database: Database): ManufacturerDataSource =
    LocalManufacturerDataSource(database)

private fun componentDataSource(database: Database): ComponentDataSource =
    LocalComponentDataSource(database)
