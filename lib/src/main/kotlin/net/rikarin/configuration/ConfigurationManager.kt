package net.rikarin.configuration

import net.rikarin.primitives.ChangeToken

class ConfigurationManager : ConfigurationBuilder, ConfigurationRoot {
    override val providers: Iterable<ConfigurationProvider>
        get() = TODO("Not yet implemented")

    override fun reload() {
        TODO("Not yet implemented")
    }

    override val reloadToken: ChangeToken
        get() = TODO("Not yet implemented")
    override val children: Iterable<ConfigurationSection>
        get() = TODO("Not yet implemented")

    override fun get(key: String): String? {
        TODO("Not yet implemented")
    }

    override fun set(key: String, value: String?) {
        TODO("Not yet implemented")
    }

    override fun getSection(key: String): ConfigurationSection {
        TODO("Not yet implemented")
    }

    override val properties: Map<String, Any>
        get() = TODO("Not yet implemented")
    override val sources: List<ConfigurationSource>
        get() = TODO("Not yet implemented")

    override fun add(source: ConfigurationSource): ConfigurationBuilder {
        TODO("Not yet implemented")
    }

    override fun build(): ConfigurationRoot {
        TODO("Not yet implemented")
    }

}