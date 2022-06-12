package io.github.tuguzt.pcbuilder.backend.data.di

import de.mkammerer.argon2.Argon2
import de.mkammerer.argon2.Argon2Factory
import io.github.tuguzt.pcbuilder.backend.data.datasource.PasswordHashDataSource
import io.github.tuguzt.pcbuilder.backend.data.datasource.PasswordUserDataSource
import io.github.tuguzt.pcbuilder.backend.data.datasource.UserDataSource
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.Database
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.impl.Argon2PasswordHashDataSource
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.impl.LocalPasswordUserDataSource
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.impl.LocalUserDataSource
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.user.PasswordUsers
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.user.Users
import io.github.tuguzt.pcbuilder.backend.data.repository.PasswordHashRepository
import io.github.tuguzt.pcbuilder.backend.data.repository.PasswordUserRepository
import io.github.tuguzt.pcbuilder.backend.data.repository.impl.PasswordHashRepositoryImpl
import io.github.tuguzt.pcbuilder.backend.data.repository.impl.PasswordUserRepositoryImpl
import io.github.tuguzt.pcbuilder.backend.data.repository.impl.UserRepositoryImpl
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
            SchemaUtils.create(Users, PasswordUsers)
        }
        database
    } withOptions {
        createdAtStart()
    }
    single<Argon2> { Argon2Factory.create() }

    single<UserDataSource> { LocalUserDataSource(database = get()) }
    single<PasswordUserDataSource> { LocalPasswordUserDataSource(database = get()) }
    single<PasswordHashDataSource> { Argon2PasswordHashDataSource(argon2 = get()) }

    single<UserRepository<Nothing?>> { UserRepositoryImpl(dataSource = get()) }
    single<PasswordUserRepository<Nothing?>> { PasswordUserRepositoryImpl(dataSource = get()) }
    single<PasswordHashRepository> { PasswordHashRepositoryImpl(dataSource = get()) }
}
