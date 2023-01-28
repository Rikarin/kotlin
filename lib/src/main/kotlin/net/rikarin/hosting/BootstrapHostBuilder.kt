package net.rikarin.hosting

import net.rikarin.InvalidOperationException
import net.rikarin.dependencyInjeciton.ServiceCollection
import net.rikarin.dependencyInjeciton.ServiceDescriptor
import net.rikarin.nameOf
import kotlin.reflect.full.createType

internal class BootstrapHostBuilder(private val builder: HostApplicationBuilder) : HostBuilder {
    val _configureHostActions = mutableListOf<HostConfigurationAction>()
    val _configureAppActions = mutableListOf<AppConfigurationAction>()
    val _configureServicesActions = mutableListOf<ConfigureServiceAction>()

    lateinit var context: HostBuilderContext
        private set

    override val properties
        get() = context.properties

    init {
        for (descriptor in builder.services) {
            if (descriptor.serviceType == HostBuilderContext::class.createType()) { // TODO: check this
                context = descriptor.implementationInstance as HostBuilderContext
                break
            }
        }

        if (this::context.isInitialized) {
            throw InvalidOperationException("${nameOf<HostBuilderContext>()} must exists in ${nameOf<ServiceCollection>()}")
        }
    }

    override fun configureHostConfiguration(configureDelegate: HostConfigurationAction): HostBuilder {
        _configureHostActions.add(configureDelegate)
        return this
    }

    override fun configureAppConfiguration(configureDelegate: AppConfigurationAction): HostBuilder {
        _configureAppActions.add(configureDelegate)
        return this
    }

    override fun configureServices(configureDelegate: ConfigureServiceAction): HostBuilder {
        _configureServicesActions.add(configureDelegate)
        return this
    }

    override fun <TContainerBuilder> configureContainer(configureDelegate: ConfigureContainerAction<TContainerBuilder>): HostBuilder {
        throw InvalidOperationException()
    }

    override fun build() = throw InvalidOperationException()

    fun runDefaultCallbacks(): ServiceDescriptor {
        for (conf in _configureHostActions) {
            conf(builder.configuration)
        }

        for (conf in _configureAppActions) {
            conf(context, builder.configuration)
        }

        for (conf in _configureServicesActions) {
            conf(context, builder.services)
        }

        for (i in builder.services.size downTo 0) {
            val desc = builder.services[i]
            if (desc.serviceType == HostedService::class.createType()) {
                // TODO: check implementation type

                builder.services.removeAt(i)
                return desc
            }
        }

        throw InvalidOperationException("GenericWebHostedService must exist in the ${nameOf<ServiceCollection>()}")
    }
}
