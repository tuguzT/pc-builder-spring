package io.github.tuguzt.pcbuilder.backend.data.di

import io.github.tuguzt.pcbuilder.backend.data.datasource.UserDataSource
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.Database
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.impl.LocalUserDataSource
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.user.Users
import io.github.tuguzt.pcbuilder.backend.data.repository.UserRepositoryImpl
import io.github.tuguzt.pcbuilder.domain.repository.user.UserRepository
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.module.Module
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module

/**
 * Koin [module][Module] for data of the application.
 */
fun dataModule(url: String, driver: String) = module {
    single {
        val database = Database(url, driver)
        transaction(database) {
            SchemaUtils.create(Users)
        }
        database
    } withOptions {
        createdAtStart()
    }
    single<UserDataSource> { LocalUserDataSource(database = get()) }
    single<UserRepository<Nothing?>> { UserRepositoryImpl(dataSource = get()) }
}
