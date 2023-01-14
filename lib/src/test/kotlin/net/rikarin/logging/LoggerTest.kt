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

        logger.scoped("asdf") {
            logger.logDebug("Foo Bar Debug")
            logger.logDebug(Exception("foo bar"))
            logger.logDebug(Exception("foo bar"), "with msg")
        }

        logger.scoped("state asdf") {
            logger.logError { "Testing scoped delegate" }
        }

        logger.logError("test error")
        logger.scoped("foo bar %s %s", 42, 4.5f) {  }
    }
}