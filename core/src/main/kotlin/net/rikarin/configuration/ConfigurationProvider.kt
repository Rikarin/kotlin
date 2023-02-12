package net.rikarin.configuration

import net.rikarin.primitives.ChangeToken

interface ConfigurationProvider {
    val reloadToken: ChangeToken

    operator fun get(key: String): String?
    operator fun set(key: String, value: String?)
    fun load()
    fun getChildKeys(earlierKeys: Iterable<String>, parentPath: String?): Iterable<String>
}