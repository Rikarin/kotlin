package net.rikarin.options

interface OptionsSnapshot<out TOptions : Any> : Options<TOptions> {
    fun get(name: String?): TOptions
    // TODO: implement operator?
}