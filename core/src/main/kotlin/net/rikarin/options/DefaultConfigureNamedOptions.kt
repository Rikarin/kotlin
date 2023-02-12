package net.rikarin.options

open class DefaultConfigureNamedOptions<TOptions : Any>(
    val name: String?,
    val action: ((TOptions) -> Unit)?
) : ConfigureNamedOptions<TOptions> {
    override fun configure(name: String?, options: TOptions) {
        if (name == null || name == this.name) {
            action?.invoke(options)
        }
    }

    override fun configure(options: TOptions) = configure(Options.DEFAULT_NAME, options)
}