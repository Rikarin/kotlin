package net.rikarin.builder

import net.rikarin.configuration.ConfigurationManager
import net.rikarin.configuration.addInMemoryCollection
import net.rikarin.hosting.*

class WebApplicationBuilder internal constructor(
    options: WebApplicationOptions,
    configureDefaults: ((HostBuilder) -> Unit)? = null
) {
    private var _builtApplication: WebApplication? = null
    private val _hostApplicaitonBuilder: HostApplicationBuilder

    val services get() = _hostApplicaitonBuilder.services
    val configuration get() = _hostApplicaitonBuilder.configuration

    init {
        val configuration = ConfigurationManager()
        // TODO: apply environment variables to configuration

        _hostApplicaitonBuilder = HostApplicationBuilder(HostApplicationBuilderSettings(
            args = options.args,
            applicationName = options.applicationName,
            environmentName = options.environmentName,
            contentRootPath = options.contentRootPath,
            configuration = configuration
        ))

        if (options.webRootPath != null) {
            configuration.addInMemoryCollection(mapOf(WebHostDefaults.WEB_ROOT_KEY to options.webRootPath))
        }

        val bootstrapHostBuilder = BootstrapHostBuilder(_hostApplicaitonBuilder)
        configureDefaults?.invoke(bootstrapHostBuilder)

//        bootstrapHostBuilder.confi

        // TODO
    }

    fun build(): WebApplication {
//        _hostApplicationBuilder.Services.Add(_genericWebHostServiceDescriptor);
//        Host.ApplyServiceProviderFactory(_hostApplicationBuilder);

        _builtApplication = WebApplication(_hostApplicaitonBuilder.build())
        return _builtApplication!!
    }
}