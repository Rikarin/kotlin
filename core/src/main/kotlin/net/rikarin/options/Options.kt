package net.rikarin.options

interface Options<out TOptions : Any> {
    val value: TOptions

    companion object {
        const val DEFAULT_NAME = ""

        fun <TOptions : Any> create(options: TOptions) = OptionsWrapper(options)
    }
}