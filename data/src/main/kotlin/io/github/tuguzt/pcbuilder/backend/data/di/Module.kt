package io.github.tuguzt.pcbuilder.backend.data.di

import de.mkammerer.argon2.Argon2
import de.mkammerer.argon2.Argon2Factory
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.Database
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.component.Components
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.component.Manufacturers
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.user.PasswordUsers
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.user.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.module.KoinDefinition
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
    singleDatabase(url, driver) withOptions {
        createdAtStart()
    }
    singleOf(::argon2)

    declareDataSources()
    declareRepositories()
}

private fun argon2(): Argon2 = Argon2Factory.create()

private fun Module.singleDatabase(url: String, driver: KClass<*>): KoinDefinition<Database> = single {
    val database = Database(url, driver)
    transaction(database) {
        SchemaUtils.create(Users, PasswordUsers, Manufacturers, Components)
    }
    database
}
