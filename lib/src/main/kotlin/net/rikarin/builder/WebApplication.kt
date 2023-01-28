package net.rikarin.builder

import net.rikarin.configuration.Configuration
import net.rikarin.dependencyInjeciton.ServiceProvider
import net.rikarin.dependencyInjeciton.getRequiredService
import net.rikarin.hosting.Host
import net.rikarin.hosting.HostApplicationLifetime
import net.rikarin.hosting.WebHostEnvironment
import net.rikarin.http.ApplicationBuilder
import net.rikarin.http.RequestDelegate
import net.rikarin.http.Server
import net.rikarin.logging.LoggerFactory

internal const val GLOBAL_ENDPOINT_ROUTE_BUILDER_KEY = "__GlobalEndpointRouteBuilder";

class WebApplication(private val host: Host) : ApplicationBuilder, Host {
    private val applicationBuilder: ApplicationBuilder

    val configuration get() = services.getRequiredService<Configuration>()
    val environment get() = services.getRequiredService<WebHostEnvironment>()
    val lifetime get() = services.getRequiredService<HostApplicationLifetime>()
    // TODO: urls, datasources

    val logger = host.services.getRequiredService<LoggerFactory>().createLogger(environment.applicationName)

    override val services: ServiceProvider get() = host.services
    override val serverFeatures get() = services.getRequiredService<Server>().features
    override val properties get() = applicationBuilder.properties

    override var applicationServices: ServiceProvider
        get() = applicationBuilder.applicationServices
        set(value) {
            applicationBuilder.applicationServices = value
        }

    init {
        applicationBuilder = DefaultApplicationBuilder(host.services, serverFeatures)
        properties[GLOBAL_ENDPOINT_ROUTE_BUILDER_KEY] = this
    }

    suspend fun run() {
        // TODO: use HostingAbstractionsHostExtensions to start this but I'm not sure how to handle CancellationTokens
        host.start()
    }

    override fun use(middleware: (RequestDelegate) -> RequestDelegate): ApplicationBuilder {
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
