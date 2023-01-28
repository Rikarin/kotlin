package net.rikarin.dependencyInjeciton.serviceLookup

import net.rikarin.Disposable
import net.rikarin.InvalidOperationException
import net.rikarin.ObjectDisposedException
import net.rikarin.dependencyInjeciton.DefaultServiceProvider2
import net.rikarin.dependencyInjeciton.ServiceProvider
import net.rikarin.dependencyInjeciton.ServiceScope
import net.rikarin.dependencyInjeciton.ServiceScopeFactory
import kotlin.reflect.KType

class ServiceProviderEngineScope(
    val rootProvider: DefaultServiceProvider2,
    val isRootScope: Boolean
) : ServiceScope, ServiceProvider, ServiceScopeFactory {
    private var _disposed = false

    internal val disposables = mutableListOf<Any>()
    internal val resolvedServices = mutableMapOf<ServiceCacheKey, Any?>()

    internal val sync: Any
        get() = resolvedServices

    override val serviceProvider
        get() = this

    override fun getService(type: KType): Any? {
        ensureNotDisposed()
        return rootProvider.getService(type, this)
    }

    override fun createScope() = rootProvider.createScope()

    override fun dispose() {
        val toDispose = beginDispose()
        if (toDispose != null) {
            for (disposable in toDispose.reversed()) {
                if (disposable is Disposable) {
                    disposable.dispose()
                } else {
                    throw InvalidOperationException()
                }
            }
        }
    }

    internal fun captureDisposable(service: Any?): Any? {
        if (service == this || service !is Disposable) {
            return service
        }

        var disposed = false
        synchronized(sync) {
            if (_disposed) {
                disposed = true
            } else {
                disposables.add(service)
            }
        }

        if (disposed) {
            if (service is Disposable) {
                service.dispose()
            }

            throw ObjectDisposedException()
        }

        return service
    }

    private fun ensureNotDisposed() {
        if (_disposed) {
            throw ObjectDisposedException()
        }
    }

    private fun beginDispose(): List<Any>? {
        synchronized(sync) {
            if (_disposed) {
                return null
            }

            // TODO: EventLog
            _disposed = true
        }

        if (isRootScope && rootProvider.isDisposed) {
            rootProvider.dispose()
        }

        return disposables
    }
}