package net.rikarin


open class SystemException(message: String? = null) : Exception(message)
open class InvalidOperationException(message: String? = null) : SystemException(message)
class ObjectDisposedException(message: String? = null) : InvalidOperationException(message)

class AggregateException(message: String? = null, vararg val innerExceptions: Exception) : Exception(message)