package net.rikarin.configuration.implementation

import net.rikarin.configuration.ConfigurationBuilder
import net.rikarin.configuration.ConfigurationProvider
import net.rikarin.configuration.ConfigurationRoot
import net.rikarin.configuration.ConfigurationSource

class DefaultConfigurationBuilder : ConfigurationBuilder {
    override val properties = mutableMapOf<String, Any>()
    override val sources = mutableListOf<ConfigurationSource>()

    override fun add(source: ConfigurationSource): ConfigurationBuilder {
        sources.add(source)
        return this
    }

    override fun build(): ConfigurationRoot {
        val providers = mutableListOf<ConfigurationProvider>()

        for (source in sources) {
            providers.add(source.build(this))
        }

        return DefaultConfigurationRoot(providers)
    }
}
