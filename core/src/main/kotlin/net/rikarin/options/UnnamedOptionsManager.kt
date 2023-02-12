package net.rikarin.options

class UnnamedOptionsManager<TOptions : Any>(private val factory: OptionsFactory<TOptions>) : Options<TOptions> {
    private var _value: TOptions? = null

    override val value: TOptions
        get() {
            if (_value == null) {
                // TODO: interlocked
                _value = factory.create(Options.DEFAULT_NAME)
            }

            return _value as TOptions
        }
}