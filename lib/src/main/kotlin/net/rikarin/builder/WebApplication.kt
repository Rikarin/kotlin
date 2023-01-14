package net.rikarin.builder

class WebApplication {
    companion object {
        fun create(args: Array<String>? = null) = createBuilder(args).build()


//        fun createBuilder() = WebApplicationBuilder(WebApplicationOptions())
        fun createBuilder(args: Array<String>? = null) = WebApplicationBuilder(WebApplicationOptions(args))
        fun createBuilder(options: WebApplicationOptions) = WebApplicationBuilder(options)
    }
}




class WebApplicationBuilder(options: WebApplicationOptions, configureOptions: ((options: Any) -> Unit)? = null) {

    fun build(): WebApplication {
        TODO()
    }
}