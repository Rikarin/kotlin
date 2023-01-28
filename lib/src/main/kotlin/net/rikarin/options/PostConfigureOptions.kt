package net.rikarin.options

interface PostConfigureOptions<in TOptions : Any> {
    fun postConfigure(name: String?, options: TOptions)
}