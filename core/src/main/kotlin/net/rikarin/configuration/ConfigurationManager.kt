package net.rikarin.configuration

import net.rikarin.Disposable
import net.rikarin.configuration.implementation.ConfigurationReloadToken
import net.rikarin.configuration.implementation.DefaultConfigurationRoot
import net.rikarin.configuration.implementation.DefaultConfigurationSection
import net.rikarin.configuration.implementation.MemoryConfigurationSource
import net.rikarin.core.exchange
import net.rikarin.primitives.ChangeToken
import net.rikarin.use
import java.util.concurrent.atomic.AtomicReference

class ConfigurationManager : ConfigurationBuilder, ConfigurationRoot, Disposable {
    private val _sources = ConfigurationSources(this)
    private val _properties = ConfigurationBuilderProperties(this)
    private val _providerManager = ReferenceCountedProviderManager()
    private val _changeToken = AtomicReference(ConfigurationReloadToken())
    private val _changeTokenRegistrations = mutableListOf<Disposable>()

    override val properties: Map<String, Any> get() = _properties
    override val sources: List<ConfigurationSource> get() = _sources
    override val reloadToken: ChangeToken get() = _changeToken.get()
    override val children: Iterable<ConfigurationSection> get() = getChildrenImplementation(null)
    override val providers: Iterable<ConfigurationProvider> get() = _providerManager.nonReferenceCountedProviders

    init {
        _sources.add(MemoryConfigurationSource())
    }

    override fun reload() {
        _providerManager.getReference().use {
            for (provider in it.providers) {
                provider.load()
            }
        }

        raiseChanged()
    }

    override fun get(key: String): String? {
        val reference = _providerManager.getReference()
        return DefaultConfigurationRoot.getConfiguration(reference.providers, key)
    }

    override fun set(key: String, value: String?) {
        val reference = _providerManager.getReference()
        DefaultConfigurationRoot.setConfiguration(reference.providers, key, value)
    }

    override fun getSection(key: String): ConfigurationSection = DefaultConfigurationSection(this, key)

    override fun add(source: ConfigurationSource): ConfigurationBuilder {
        _sources.add(source)
        return this
    }

    override fun build(): ConfigurationRoot = this

    internal fun getProvidersReference() = _providerManager.getReference()

    override fun dispose() {
        disposeRegistrations()
        _providerManager.dispose()
    }

    private fun addSource(source: ConfigurationSource) {
        val provider = source.build(this)
        provider.load()

        _changeTokenRegistrations.add(ChangeToken.onChange(provider::reloadToken, ::raiseChanged))
        _providerManager.addProvider(provider)
        raiseChanged()
    }

    private fun reloadSources() {
        disposeRegistrations()
        _changeTokenRegistrations.clear()

        val newProvidersList = mutableListOf<ConfigurationProvider>()
        for (source in _sources) {
            newProvidersList.add(source.build(this))
        }

        for (p in newProvidersList) {
            p.load()
            _changeTokenRegistrations.add(ChangeToken.onChange(p::reloadToken, ::raiseChanged))
        }

        _providerManager.replaceProviders(newProvidersList)
        raiseChanged()
    }

    private fun disposeRegistrations() {
        for (registration in _changeTokenRegistrations) {
            registration.dispose()
        }
    }

    private fun raiseChanged() {
        val previousToken = _changeToken.exchange(ConfigurationReloadToken())
        previousToken.onReload()
    }


    private class ConfigurationSources(private val config: ConfigurationManager) : MutableList<ConfigurationSource> {
        private val _sources = mutableListOf<ConfigurationSource>()

        override val size: Int get() = _sources.size

        override fun get(index: Int): ConfigurationSource = _sources.get(index)
        override fun isEmpty(): Boolean = _sources.isEmpty()
        override fun iterator(): MutableIterator<ConfigurationSource> = _sources.iterator()
        override fun listIterator(): MutableListIterator<ConfigurationSource> = _sources.listIterator()
        override fun listIterator(index: Int): MutableListIterator<ConfigurationSource> = _sources.listIterator(index)
        override fun lastIndexOf(element: ConfigurationSource): Int = _sources.lastIndexOf(element)
        override fun indexOf(element: ConfigurationSource): Int = _sources.indexOf(element)
        override fun containsAll(elements: Collection<ConfigurationSource>): Boolean = _sources.containsAll(elements)
        override fun contains(element: ConfigurationSource): Boolean = _sources.contains(element)
        override fun subList(fromIndex: Int, toIndex: Int): MutableList<ConfigurationSource> =
            _sources.subList(fromIndex, toIndex)

        override fun clear() {
            _sources.clear()
            config.reloadSources()
        }

        override fun addAll(elements: Collection<ConfigurationSource>): Boolean {
            val ret = _sources.addAll(elements)
            config.reloadSources()

            return ret
        }

        override fun addAll(index: Int, elements: Collection<ConfigurationSource>): Boolean {
            val ret = _sources.addAll(index, elements)
            config.reloadSources()

            return ret
        }

        override fun add(index: Int, element: ConfigurationSource) {
            val ret = _sources.add(index, element)
            config.reloadSources()

            return ret
        }

        override fun add(element: ConfigurationSource): Boolean {
            val ret = _sources.add(element)
            config.reloadSources()

            return ret
        }

        override fun removeAt(index: Int): ConfigurationSource {
            val ret = _sources.removeAt(index)
            config.reloadSources()

            return ret
        }

        override fun set(index: Int, element: ConfigurationSource): ConfigurationSource {
            val ret = _sources.set(index, element)
            config.reloadSources()

            return ret
        }

        override fun retainAll(elements: Collection<ConfigurationSource>): Boolean {
            val ret = _sources.retainAll(elements)
            config.reloadSources()

            return ret
        }

        override fun removeAll(elements: Collection<ConfigurationSource>): Boolean {
            val ret = _sources.removeAll(elements)
            config.reloadSources()

            return ret
        }

        override fun remove(element: ConfigurationSource): Boolean {
            val ret = _sources.remove(element)
            config.reloadSources()

            return ret
        }
    }


    private class ConfigurationBuilderProperties(private val config: ConfigurationManager) : MutableMap<String, Any> {
        private val _properties = mutableMapOf<String, Any>()

        override val entries: MutableSet<MutableMap.MutableEntry<String, Any>> get() = _properties.entries
        override val keys: MutableSet<String> get() = _properties.keys
        override val size: Int get() = _properties.size
        override val values: MutableCollection<Any> get() = _properties.values

        override fun isEmpty(): Boolean = _properties.isEmpty()
        override fun get(key: String): Any? = _properties.get(key)
        override fun containsValue(value: Any): Boolean = _properties.containsValue(value)
        override fun containsKey(key: String): Boolean = _properties.containsKey(key)

        override fun clear() {
            _properties.clear()
            config.reloadSources()
        }

        override fun remove(key: String): Any? {
            val ret = _properties.remove(key)
            config.reloadSources()

            return ret
        }

        override fun putAll(from: Map<out String, Any>) {
            val ret = _properties.putAll(from)
            config.reloadSources()

            return ret
        }

        override fun put(key: String, value: Any): Any? {
            val ret = _properties.put(key, value)
            config.reloadSources()

            return ret
        }
    }
}