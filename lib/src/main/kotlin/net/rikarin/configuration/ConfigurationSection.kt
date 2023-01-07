package net.rikarin.configuration

interface ConfigurationSection : Configuration {
    val key: String
    val path: String
    var value: String?
}