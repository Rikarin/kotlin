package net.rikarin.configuration.implementation

import net.rikarin.configuration.ChangeToken
import net.rikarin.configuration.ConfigurationProvider
import net.rikarin.configuration.ConfigurationRoot
import net.rikarin.configuration.ConfigurationSection
import net.rikarin.core.exchange
import java.util.concurrent.atomic.AtomicReference

class DefaultConfigurationRoot(providers: List<ConfigurationProvider>) : ConfigurationRoot, AutoCloseable {
    private val _providers: List<ConfigurationProvider>
    private val _changeToken = AtomicReference(ConfigurationChangeToken())
    private val _changeTokenRegistrations = mutableListOf<AutoCloseable>()

    init {
        _providers = providers

        for (p in providers) {
            p.load()
            // TODO
//            _changeTokenRegistrations.add(ChangeToken)
        }
    }

    override val providers
        get() = _providers

    override fun reload() {
        for (p in _providers) {
            p.load()
        }

        raiseChanged()
    }

    override val reloadToken: ChangeToken
        get() = _changeToken.get()

    override val children
        get() = getChildrenImplementation(null)

    override fun get(key: String): String? {
        for (i in providers.size - 1 downTo 0) {
            val value = providers[i].getOrNull(key)

            if (value != null) {
                return value
            }
        }

        return null
    }

    override fun set(key: String, value: String?) {
        if (providers.isEmpty()) {
            throw Exception("no providers")
        }

        for (p in providers) {
            p.set(key, value)
        }
    }

    override fun getSection(key: String) = DefaultConfigurationSection(this, key)

    override fun close() {
        TODO("Not yet implemented")
    }

    private fun raiseChanged() {
        val previousToken = _changeToken.exchange(ConfigurationChangeToken())
        previousToken.onReload()
    }
}