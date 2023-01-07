package net.rikarin.logging

interface LoggerFactory {
    fun createLogger(categoryName: String): Logger
    fun addProvider(provider: LoggerProvider)
}
