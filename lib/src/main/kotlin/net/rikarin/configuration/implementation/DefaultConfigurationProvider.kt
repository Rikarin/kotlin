package net.rikarin.configuration.implementation

import net.rikarin.configuration.ConfigurationPath
import net.rikarin.configuration.ConfigurationProvider
import net.rikarin.core.exchange
import net.rikarin.primitives.ChangeToken
import java.util.concurrent.atomic.AtomicReference

abstract class DefaultConfigurationProvider : ConfigurationProvider {
    private val _reloadToken = AtomicReference(ConfigurationReloadToken())

    protected val data = mutableMapOf<String, String?>() // TODO: ignore case

    override val reloadToken: ChangeToken
        get() = _reloadToken.get()

    override fun get(key: String) = data[key]

    override fun set(key: String, value: String?) {
        data[key] = value
    }

    override fun load() { }

    override fun getChildKeys(earlierKeys: Iterable<String>, parentPath: String?): Iterable<String> {
        val results = mutableListOf<String>()

        if (parentPath == null) {
            for (kv in data) {
                results.add(segment(kv.key, 0))
            }
        } else {
            for (kv in data) {
                if (kv.key.length > parentPath.length &&
                    kv.key.startsWith(parentPath, ignoreCase = true) &&
                    kv.key[parentPath.length] == ':') {
                    results.add(segment(kv.key, parentPath.length + 1))
                }
            }
        }

        results.addAll(earlierKeys)
        // TODO
//        results.Sort(ConfigurationKeyComparer.Comparison);

        return results
    }

    protected fun onReload() {
        val previousToken = _reloadToken.exchange(ConfigurationReloadToken())
        previousToken.onReload()
    }

    companion object {
        fun segment(key: String, prefixLength: Int): String {
            val idx = key.indexOf(ConfigurationPath.KEY_DELIMITER, prefixLength, ignoreCase = true)
            return if (idx < 0) key.substring(prefixLength) else key.substring(prefixLength, idx)
        }
    }
}

