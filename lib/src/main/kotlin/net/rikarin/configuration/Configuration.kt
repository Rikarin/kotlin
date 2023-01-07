package net.rikarin.configuration

interface Configuration {
    val reloadToken: ChangeToken
    val children: Iterable<ConfigurationSection>

    operator fun get(key: String): String?
    operator fun set(key: String, value: String?)

    fun getSection(key: String): ConfigurationSection
}
