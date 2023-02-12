package net.rikarin.configuration

import net.rikarin.Disposable
import net.rikarin.ObjectDisposedException

internal class ReferenceCountedProviderManager : Disposable {
    private val _replaceProvidersLock = Any()
    private var _refCountedProviders = ReferenceCountedProviders.create(listOf())
    private var _disposed = false

    val nonReferenceCountedProviders = _refCountedProviders.nonReferenceCountedProviders

    fun getReference(): ReferenceCountedProviders {
        synchronized(_replaceProvidersLock) {
            if (_disposed) {
                return ReferenceCountedProviders.createDisposed(_refCountedProviders.nonReferenceCountedProviders)
            }

            _refCountedProviders.addReference()
            return _refCountedProviders
        }
    }

    fun replaceProviders(providers: List<ConfigurationProvider>) {
        val old = _refCountedProviders
        synchronized(_replaceProvidersLock) {
            if (_disposed) {
                throw ObjectDisposedException("ConfigurationManager")
            }

            _refCountedProviders = ReferenceCountedProviders.create(providers)
        }

        old.dispose()
    }

    fun addProvider(provider: ConfigurationProvider) {
        synchronized(_replaceProvidersLock) {
            if (_disposed) {
                throw ObjectDisposedException("ConfigurationManager")
            }

            _refCountedProviders.providers = mutableListOf(*_refCountedProviders.providers.toTypedArray(), provider)
        }
    }

    override fun dispose() {
        val old = _refCountedProviders
        synchronized(_replaceProvidersLock) {
            _disposed = true
        }

        old.dispose()
    }
}