package net.rikarin.logging

import org.junit.jupiter.api.Test

class LoggerTest {
    @Test
    fun createLogger() {
        val loggerFactory = SingleLoggerFactory()
        loggerFactory.addProvider(ConsoleLoggerProvider())
        val logger = loggerFactory.createLogger("FooBar")

        logger.log(LogLevel.CRITICAL, null, "Foo Bar")
        logger.log(LogLevel.ERROR) { "foo ${2 + 2} bar" }

        logger.beginScope("asdf").use {
            logger.logDebug("Foo Bar Debug")
            logger.logDebug(Exception("foo bar"))
            logger.logDebug(Exception("foo bar"), "with msg")
        }

        logger.logError("test error")
        logger.beginScope("foo bar %s %s", 42, 4.5f).use {  }
    }
}