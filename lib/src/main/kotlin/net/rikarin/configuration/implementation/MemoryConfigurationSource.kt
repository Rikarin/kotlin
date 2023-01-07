package net.rikarin.configuration.implementation

import net.rikarin.configuration.ConfigurationBuilder
import net.rikarin.configuration.ConfigurationSource

class MemoryConfigurationSource(val initialData: Map<String, String?>? = null) : ConfigurationSource {
    override fun build(builder: ConfigurationBuilder) = MemoryConfigurationProvider(this)
}