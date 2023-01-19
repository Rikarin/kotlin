package net.rikarin.configuration.implementation

import net.rikarin.primitives.ChangeToken
import net.rikarin.configuration.ConfigurationPath
import net.rikarin.configuration.ConfigurationRoot
import net.rikarin.configuration.ConfigurationSection

class DefaultConfigurationSection(private val root: ConfigurationRoot, path: String) : ConfigurationSection {
    val _path: String

    init {
        _path = path
    }

    override val key
        get() = ConfigurationPath.getSectionKey(path)

    override val path
        get() = _path

    override var value: String?
        get() = root[path]
        set(value) = root.set(path, value)

    override val reloadToken: ChangeToken = root.reloadToken

    override val children
        get() = root.getChildrenImplementation(path)

    override fun get(key: String) = root.get(ConfigurationPath.combine(path, key))
    override fun set(key: String, value: String?) = root.set(ConfigurationPath.combine(path, key), value)
    override fun getSection(key: String) = root.getSection(ConfigurationPath.combine(path, key))
}