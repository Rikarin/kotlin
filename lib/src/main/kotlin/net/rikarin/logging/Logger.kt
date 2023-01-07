package net.rikarin.logging

interface Logger {
    fun <TState> log(logLevel: LogLevel, state: TState, exception: Throwable?, formatter: (TState, Throwable?) -> String)
    fun isEnabled(logLevel: LogLevel): Boolean
    fun <TState> beginScope(state: TState): AutoCloseable
}

// ----------------------------------------------------- TRACE ------------------------------------------------ //
fun Logger.logTrace(message: String, vararg args: Any) = log(LogLevel.TRACE, message, args)
fun Logger.logTrace(exception: Throwable, message: String? = null, vararg args: Any) = log(LogLevel.TRACE, exception, message, args)
fun Logger.logTrace(message: () -> Any) = log(LogLevel.TRACE, message)
fun Logger.logTrace(exception: Throwable, message: () -> Any) = log(LogLevel.TRACE, exception, message)

// ----------------------------------------------------- DEBUG ------------------------------------------------ //
fun Logger.logDebug(message: String, vararg args: Any) = log(LogLevel.DEBUG, message, args)
fun Logger.logDebug(exception: Throwable, message: String? = null, vararg args: Any) = log(LogLevel.DEBUG, exception, message, args)
fun Logger.logDebug(message: () -> Any) = log(LogLevel.DEBUG, message)
fun Logger.logDebug(exception: Throwable, message: () -> Any) = log(LogLevel.DEBUG, exception, message)

// ----------------------------------------------------- INFO ------------------------------------------------- //
fun Logger.logInfo(message: String, vararg args: Any) = log(LogLevel.INFO, message, args)
fun Logger.logInfo(exception: Throwable, message: String? = null, vararg args: Any) = log(LogLevel.INFO, exception, message, args)
fun Logger.logInfo(message: () -> Any) = log(LogLevel.INFO, message)
fun Logger.logInfo(exception: Throwable, message: () -> Any) = log(LogLevel.INFO, exception, message)

// ----------------------------------------------------- WARNING ---------------------------------------------- //
fun Logger.logWarning(message: String, vararg args: Any) = log(LogLevel.WARNING, message, args)
fun Logger.logWarning(exception: Throwable, message: String? = null, vararg args: Any) = log(LogLevel.WARNING, exception, message, args)
fun Logger.logWarning(message: () -> Any) = log(LogLevel.WARNING, message)
fun Logger.logWarning(exception: Throwable, message: () -> Any) = log(LogLevel.WARNING, exception, message)

// ----------------------------------------------------- ERROR ------------------------------------------------ //
fun Logger.logError(message: String, vararg args: Any) = log(LogLevel.ERROR, message, args)
fun Logger.logError(exception: Throwable, message: String? = null, vararg args: Any) = log(LogLevel.ERROR, exception, message, args)
fun Logger.logError(message: () -> Any) = log(LogLevel.ERROR, message)
fun Logger.logError(exception: Throwable, message: () -> Any) = log(LogLevel.ERROR, exception, message)

// ----------------------------------------------------- CRITICAL --------------------------------------------- //
fun Logger.logCritical(message: String, vararg args: Any) = log(LogLevel.CRITICAL, message, args)
fun Logger.logCritical(exception: Throwable, message: String? = null, vararg args: Any) = log(LogLevel.CRITICAL, exception, message, args)
fun Logger.logCritical(message: () -> Any) = log(LogLevel.CRITICAL, message)
fun Logger.logCritical(exception: Throwable, message: () -> Any) = log(LogLevel.CRITICAL, exception, message)

// ----------------------------------------------------- LOG -------------------------------------------------- //
fun Logger.log(logLevel: LogLevel, message: String, vararg args: Any) = log(logLevel, null, message, args)
fun Logger.log(logLevel: LogLevel, exception: Throwable?, message: String? = null, vararg args: Any) {
    if (isEnabled(logLevel)) {
        log(logLevel, FormattedLogValues(message, args), exception) { state, _ -> state.toString() }
    }
}

// log { "foo $bar" }
fun Logger.log(logLevel: LogLevel, message: () -> Any) = log(logLevel, null, message)
fun Logger.log(logLevel: LogLevel, exception: Throwable?, message: () -> Any) {
    if (isEnabled(logLevel)) {
        log(logLevel, message(), exception) { state, _ -> state.toString() }
    }
}

//fun Logger.beginScope(message: () -> Any) = beginScope(message())
fun Logger.beginScope(message: String, vararg args: Any) = beginScope(FormattedLogValues(message, args))


private class FormattedLogValues(private val format: String?, private vararg val args: Any) {
    override fun toString(): String {
        return String.format(format ?: "", args)
    }
}
