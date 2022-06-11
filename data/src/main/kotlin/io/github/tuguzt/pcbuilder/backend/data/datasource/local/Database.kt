package io.github.tuguzt.pcbuilder.backend.data.datasource.local

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig
import org.jetbrains.exposed.sql.transactions.ThreadLocalTransactionManager
import org.jetbrains.exposed.sql.transactions.TransactionManager
import java.sql.Connection

/**
 * Creates new instance of [Database].
 */
internal fun Database(
    url: String,
    driver: String,
    username: String = "",
    password: String = "",
    maxPoolSize: Int = 10,
    setupConnection: (Connection) -> Unit = {},
    databaseConfig: DatabaseConfig? = null,
    manager: (Database) -> TransactionManager = { ThreadLocalTransactionManager(it) },
): Database {
    val datasource = hikariDataSource(url, driver, username, password, maxPoolSize)
    return Database.connect(datasource, setupConnection, databaseConfig, manager)
}

private fun hikariDataSource(
    url: String,
    driver: String,
    username: String,
    password: String,
    maxPoolSize: Int,
): HikariDataSource {
    val config = HikariConfig().apply {
        jdbcUrl = url
        driverClassName = driver
        this.username = username
        this.password = password
        maximumPoolSize = maxPoolSize
    }
    return HikariDataSource(config)
}
