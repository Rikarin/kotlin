package net.rikarin.options

interface OptionsFactory<TOptions : Any> {
    fun create(name: String): TOptions
}