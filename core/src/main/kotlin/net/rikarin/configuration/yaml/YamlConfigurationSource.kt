package net.rikarin.configuration.yaml

import net.rikarin.configuration.ConfigurationBuilder
import net.rikarin.configuration.fileExtensions.FileConfigurationSource

class YamlConfigurationSource : FileConfigurationSource() {
    override fun build(builder: ConfigurationBuilder) = YamlConfigurationProvider(this)
}
