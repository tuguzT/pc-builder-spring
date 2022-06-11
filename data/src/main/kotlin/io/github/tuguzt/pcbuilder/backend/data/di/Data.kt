package io.github.tuguzt.pcbuilder.backend.data.di

import io.github.tuguzt.pcbuilder.backend.data.datasource.local.Database
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.impl.LocalUserDataSource
import io.github.tuguzt.pcbuilder.backend.data.repository.UserRepositoryImpl
import io.github.tuguzt.pcbuilder.domain.repository.user.UserRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module

/**
 * Koin [module][Module] for data of the application.
 */
fun dataModule(url: String, driver: String, username: String = "", password: String = "") = module {
    single { Database(url, driver, username, password) } withOptions {
        createdAtStart()
    }
    singleOf(::LocalUserDataSource)
    single<UserRepository<Nothing?>> { UserRepositoryImpl(dataSource = get()) }
}
