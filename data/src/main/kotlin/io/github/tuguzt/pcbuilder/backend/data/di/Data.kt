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
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.module.Module
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module
import kotlin.reflect.KClass

/**
 * Koin [module][Module] for data of the application.
 */
fun dataModule(url: String, driver: KClass<*>) = module {
    single {
        val database = Database(url, driver)
        transaction(database) {
            SchemaUtils.create(Users, PasswordUsers)
        }
        database
    } withOptions {
        createdAtStart()
    }
    singleOf(::argon2)

    singleOf(::userDataSource)
    singleOf(::passwordUserDataSource)
    singleOf(::passwordHashDataSource)

    singleOf(::userRepository)
    singleOf(::passwordUserRepository)
    singleOf(::passwordHashRepository)
}

private fun argon2(): Argon2 = Argon2Factory.create()

private fun userDataSource(database: Database): UserDataSource = LocalUserDataSource(database)

private fun passwordUserDataSource(database: Database): PasswordUserDataSource = LocalPasswordUserDataSource(database)

private fun passwordHashDataSource(argon2: Argon2): PasswordHashDataSource = Argon2PasswordHashDataSource(argon2)

private fun userRepository(dataSource: UserDataSource): UserRepository<Nothing?> = UserRepositoryImpl(dataSource)

private fun passwordUserRepository(dataSource: PasswordUserDataSource): PasswordUserRepository<Nothing?> =
    PasswordUserRepositoryImpl(dataSource)

private fun passwordHashRepository(dataSource: PasswordHashDataSource): PasswordHashRepository =
    PasswordHashRepositoryImpl(dataSource)
