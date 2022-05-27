package io.github.tuguzt.pcbuilder.backend.spring

import mu.KLogger
import org.koin.core.KoinApplication
import org.koin.core.logger.Level
import org.koin.core.logger.Logger as KoinLogger

fun KoinApplication.kLogger(logger: KLogger, level: Level = Level.INFO) = logger(KoinLoggerImpl(logger, level))

private class KoinLoggerImpl(private val kLogger: KLogger, level: Level) : KoinLogger(level) {
    override fun log(level: Level, msg: String) = when (level) {
        Level.DEBUG -> kLogger.debug { msg }
        Level.INFO -> kLogger.info { msg }
        Level.ERROR -> kLogger.error { msg }
        Level.NONE -> kLogger.trace { msg }
    }
}
