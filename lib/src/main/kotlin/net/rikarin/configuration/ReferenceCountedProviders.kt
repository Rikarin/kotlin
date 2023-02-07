package net.rikarin.configuration

import net.rikarin.Disposable
import java.util.concurrent.atomic.AtomicLong

internal abstract class ReferenceCountedProviders : Disposable {
    abstract var providers: List<ConfigurationProvider>
    abstract val nonReferenceCountedProviders: List<ConfigurationProvider>

    abstract fun addReference()

    companion object {
        fun create(providers: List<ConfigurationProvider>): ReferenceCountedProviders =
            ActiveReferenceCountedProviders(providers)

        fun createDisposed(providers: List<ConfigurationProvider>): ReferenceCountedProviders =
            DisposedReferenceCountedProviders(providers)
    }
}


private class ActiveReferenceCountedProviders(providers: List<ConfigurationProvider>) : ReferenceCountedProviders() {
    private var _refCount = AtomicLong(1)
    @Volatile private var _providers: List<ConfigurationProvider>

    override val nonReferenceCountedProviders get() = _providers
    override var providers: List<ConfigurationProvider>
        get() {
            assert(_refCount.get() > 0)
            return _providers
        }
        set(value) {
            assert(_refCount.get() > 0)
            _providers = value
        }

    init {
        _providers = providers
    }

    override fun addReference() {
        assert(_refCount.get() > 0)
        _refCount.incrementAndGet()
    }

    override fun dispose() {
        if (_refCount.decrementAndGet() == 0L) {
            for (provider in _providers) {
                if (provider is Disposable) {
                    provider.dispose()
                }
            }
        }
    }
}


private class DisposedReferenceCountedProviders(override var providers: List<ConfigurationProvider>) : ReferenceCountedProviders() {
    override val nonReferenceCountedProviders get() = providers
    override fun addReference() { }
    override fun dispose() { }
}