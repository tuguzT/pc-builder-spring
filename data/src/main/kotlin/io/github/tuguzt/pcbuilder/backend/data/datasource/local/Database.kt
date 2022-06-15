package io.github.tuguzt.pcbuilder.backend.data.datasource.local

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig
import org.jetbrains.exposed.sql.transactions.ThreadLocalTransactionManager
import org.jetbrains.exposed.sql.transactions.TransactionManager
import java.sql.Connection
import kotlin.reflect.KClass

/**
 * Creates new instance of [Database].
 */
internal fun Database(
    url: String,
    driver: KClass<*>,
    maxPoolSize: Int = 10,
    setupConnection: (Connection) -> Unit = {},
    databaseConfig: DatabaseConfig? = null,
    manager: (Database) -> TransactionManager = { ThreadLocalTransactionManager(it) },
): Database {
    val datasource = HikariDataSource(url, requireNotNull(driver.qualifiedName), maxPoolSize)
    return Database.connect(datasource, setupConnection, databaseConfig, manager)
}

private fun HikariDataSource(
    url: String,
    driver: String,
    maxPoolSize: Int,
): HikariDataSource {
    val config = HikariConfig().apply {
        jdbcUrl = url
        driverClassName = driver
        maximumPoolSize = maxPoolSize
    }
    return HikariDataSource(config)
}
