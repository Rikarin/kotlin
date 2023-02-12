package net.rikarin.options

class OptionsManager<TOptions : Any>(private val factor: OptionsFactory<TOptions>) : Options<TOptions>,
    OptionsSnapshot<TOptions> {
    private val _cache = OptionsCache<TOptions>()

    override fun get(name: String?): TOptions {
        val n = name ?: Options.DEFAULT_NAME

        TODO("Not yet implemented")
    }

    override val value: TOptions get() = get(Options.DEFAULT_NAME)
}