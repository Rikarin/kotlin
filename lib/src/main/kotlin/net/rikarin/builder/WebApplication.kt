package net.rikarin.builder

import net.rikarin.configuration.Configuration
import net.rikarin.dependencyInjeciton.ServiceProvider
import net.rikarin.dependencyInjeciton.getRequiredService
import net.rikarin.hosting.Host
import net.rikarin.http.ApplicationBuilder
import net.rikarin.http.RequestDelegate
import net.rikarin.http.Server
import net.rikarin.logging.LoggerFactory

class WebApplication(private val host: Host) : ApplicationBuilder, Host {
    private val applicationBuilder: ApplicationBuilder

    override val services: ServiceProvider get() = host.services
    override val serverFeatures get() = services.getRequiredService<Server>().features

    override var applicationServices: ServiceProvider
        get() = applicationBuilder.applicationServices
        set(value) {
            applicationBuilder.applicationServices = value
        }

    override val properties: Map<String, Any?>
        get() = applicationBuilder.properties

    val configuration get() = services.getRequiredService<Configuration>()

    // TODO:  environment, lifetime, urls

    val logger = host.services.getRequiredService<LoggerFactory>().createLogger("TODO")

    init {
        applicationBuilder = DefaultApplicationBuilder(host.services, serverFeatures)
    }

    suspend fun run() {
        // TODO: use HostingAbstractionsHostExtensions to start this but I'm not sure how to handle CancellationTokens
        host.start()
    }

    override fun use(middleware: (delegate: RequestDelegate) -> RequestDelegate): ApplicationBuilder {
        applicationBuilder.use(middleware)
        return this
    }

    override fun new(): ApplicationBuilder {
//        val builder = ApplicationBuilder()
        TODO("Not yet implemented")
    }

    override fun build(): RequestDelegate {
        return applicationBuilder.build()
    }

    override suspend fun start() = host.start()
    override suspend fun stop() = host.stop()

    companion object {
        fun create(args: Array<String>? = null) = createBuilder(args).build()
        fun createBuilder(args: Array<String>? = null) = WebApplicationBuilder(WebApplicationOptions(args))
        fun createBuilder(options: WebApplicationOptions) = WebApplicationBuilder(options)
    }
}
