package net.rikarin.logging

class SingleLoggerFactory : LoggerFactory {
    private lateinit var _provider: LoggerProvider

    override fun createLogger(categoryName: String): Logger {
        return _provider.createLogger(categoryName)
    }

    override fun addProvider(provider: LoggerProvider) {
        _provider = provider
    }
}