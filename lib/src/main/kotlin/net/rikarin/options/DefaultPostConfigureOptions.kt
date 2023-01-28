package net.rikarin.options

class DefaultPostConfigureOptions<TOptions : Any>(val name: String?, val action: ((TOptions) -> Unit)?) :
    PostConfigureOptions<TOptions> {
    override fun postConfigure(name: String?, options: TOptions) {
        if (name == null || name == this.name) {
            action?.invoke(options)
        }
    }
}