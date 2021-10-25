package io.github.tuguzt.pcbuilder.backend.spring.repository

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

/**
 * Configuration of the remote database.
 */
@Configuration
class Configuration {
    companion object {
        @JvmStatic
        private val p_jdbcUrl = System.getenv("DATABASE_JDBC_URL")

        @JvmStatic
        private val p_username = System.getenv("DATABASE_USERNAME")

        @JvmStatic
        private val p_password = System.getenv("DATABASE_PASSWORD")
    }

    @Bean
    fun dataSource(): DataSource {
        val config = HikariConfig().apply {
            jdbcUrl = p_jdbcUrl
            username = p_username
            password = p_password
        }
        return HikariDataSource(config)
    }
}
