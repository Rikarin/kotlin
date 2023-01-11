package net.rikarin.configuration.yaml

import net.rikarin.configuration.ConfigurationBuilder

fun ConfigurationBuilder.addYamlFile(path: String, optional: Boolean = false, reloadOnChange: Boolean = false): ConfigurationBuilder {
    if (path.isEmpty()) {
        throw Exception("invalid path")
    }

    val source = YamlConfigurationSource()
    // file provider?
    source.path = path
    source.optional = optional
    source.reloadOnChange = reloadOnChange
    source.resolveFileProvider()

    add(source)
    return this
}
