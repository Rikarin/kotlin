package net.rikarin.logging

class ConsoleLoggerProvider : LoggerProvider {
    override fun createLogger(categoryName: String): Logger {
        return ConsoleLogger(categoryName)
    }

    private class ConsoleLogger(val categoryName: String) : Logger {
        private var _scope: Any? = null

        override fun <TState> log(logLevel: LogLevel, state: TState, exception: Throwable?, formatter: (TState, Throwable?) -> String) {
            val message = formatter(state, exception)
            val exMessage = exception?.toString() ?: ""

            println("[${logLevel.name}][$categoryName] $message $exMessage")
        }

        override fun isEnabled(logLevel: LogLevel): Boolean {
            return true
        }

        override fun <TState> scoped(state: TState, delegate: () -> Unit) {
            val scope = _scope
            _scope = state

            delegate()
            _scope = scope
        }
    }
}