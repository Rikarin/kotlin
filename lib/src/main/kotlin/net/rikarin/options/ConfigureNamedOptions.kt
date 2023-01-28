package net.rikarin.options

interface ConfigureNamedOptions<in TOptions : Any> : ConfigureOptions<TOptions> {
    fun configure(name: String?, options: TOptions)
}