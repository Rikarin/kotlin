package net.rikarin.builder

class WebApplicationBuilder internal constructor(options: WebApplicationOptions, configureOptions: ((options: Any) -> Unit)? = null) { // TODO
    private var _builtApplication: WebApplication? = null

    init {
        // TODO: do stuff

    }

    fun build(): WebApplication {
//        _hostApplicationBuilder.Services.Add(_genericWebHostServiceDescriptor);
//        Host.ApplyServiceProviderFactory(_hostApplicationBuilder);
//        _builtApplication = new WebApplication(_hostApplicationBuilder.Build());
//        return _builtApplication;

//        _builtApplication = WebApplication()
//        println("collection $")
        return _builtApplication!!
    }
}