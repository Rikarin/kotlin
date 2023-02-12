package net.rikarin.options

interface ConfigureOptions<in TOptions : Any> {
    fun configure(options: TOptions)
}