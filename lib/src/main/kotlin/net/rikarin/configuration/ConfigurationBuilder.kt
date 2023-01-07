package net.rikarin.configuration

interface ConfigurationBuilder {
    val properties: Map<String, Any>
    val sources: List<ConfigurationSource>

    fun add(source: ConfigurationSource): ConfigurationBuilder
    fun build(): ConfigurationRoot
}