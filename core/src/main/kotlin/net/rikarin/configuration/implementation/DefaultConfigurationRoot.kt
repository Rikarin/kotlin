package net.rikarin.configuration.implementation

import net.rikarin.Disposable
import net.rikarin.InvalidOperationException
import net.rikarin.NO_SOURCES
import net.rikarin.primitives.ChangeToken
import net.rikarin.configuration.ConfigurationProvider
import net.rikarin.configuration.ConfigurationRoot
import net.rikarin.configuration.getChildrenImplementation
import net.rikarin.core.exchange
import java.util.concurrent.atomic.AtomicReference

class DefaultConfigurationRoot(providers: List<ConfigurationProvider>) : ConfigurationRoot, Disposable {
    private val _providers: List<ConfigurationProvider>
    private val _changeToken = AtomicReference(ConfigurationReloadToken())
    private val _changeTokenRegistrations = mutableListOf<Disposable>()

    init {
        _providers = providers

        for (p in providers) {
            p.load()
            _changeTokenRegistrations.add(ChangeToken.onChange(p::reloadToken, ::raiseChanged))
        }
    }

    override val providers get() = _providers
    override val reloadToken: ChangeToken get() = _changeToken.get()
    override val children get() = getChildrenImplementation(null)

    override fun get(key: String): String? = getConfiguration(_providers, key)
    override fun set(key: String, value: String?) = setConfiguration(_providers, key, value)
    override fun getSection(key: String) = DefaultConfigurationSection(this, key)

    override fun reload() {
        for (p in _providers) {
            p.load()
        }

        raiseChanged()
    }

    override fun dispose() {
        for (registration in _changeTokenRegistrations) {
            registration.dispose()
        }

        for (provider in _providers) {
            if (provider is Disposable) {
                provider.dispose()
            }
        }
    }

    private fun raiseChanged() {
        val previousToken = _changeToken.exchange(ConfigurationReloadToken())
        previousToken.onReload()
    }

    companion object {
        internal fun getConfiguration(providers: List<ConfigurationProvider>, key: String): String? {
            for (provider in providers.reversed()) {
                val ret = provider[key]
                if (ret != null) {
                    return ret
                }
            }

            return null
        }

        internal fun setConfiguration(providers: List<ConfigurationProvider>, key: String, value: String?) {
            if (providers.isEmpty()) {
                throw InvalidOperationException(NO_SOURCES)
            }

            for (provider in providers) {
                provider.set(key, value)
            }
        }
    }
}