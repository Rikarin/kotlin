package net.rikarin.configuration

interface ConfigurationRoot : Configuration {
    val providers: Iterable<ConfigurationProvider>

    fun reload()
}