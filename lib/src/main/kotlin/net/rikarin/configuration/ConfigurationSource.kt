package net.rikarin.configuration

interface ConfigurationSource {
    fun build(builder: ConfigurationBuilder): ConfigurationProvider
}