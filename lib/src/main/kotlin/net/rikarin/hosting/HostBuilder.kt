package net.rikarin.hosting

import net.rikarin.configuration.ConfigurationBuilder
import net.rikarin.dependencyInjeciton.ServiceCollection

typealias HostConfigurationAction = (ConfigurationBuilder) -> Unit
typealias AppConfigurationAction = (HostBuilderContext, ConfigurationBuilder) -> Unit
typealias ConfigureServiceAction = (HostBuilderContext, ServiceCollection) -> Unit
typealias ConfigureContainerAction<TContainerBuilder> = (HostBuilderContext, TContainerBuilder) -> Unit

interface HostBuilder {
    val properties: MutableMap<Any, Any>

    fun configureHostConfiguration(configureDelegate: HostConfigurationAction): HostBuilder
    fun configureAppConfiguration(configureDelegate: AppConfigurationAction): HostBuilder
    fun configureServices(configureDelegate: ConfigureServiceAction): HostBuilder

//    fun <TContainerBuilder : Any> useServiceProviderFactory(factory: (builder: ServiceProviderFactory<TContainerBuilder>)): HostBuilder
//    IHostBuilder UseServiceProviderFactory<TContainerBuilder>(Func<HostBuilderContext, IServiceProviderFactory<TContainerBuilder>> factory) where TContainerBuilder : notnull;

    fun <TContainerBuilder> configureContainer(configureDelegate: ConfigureContainerAction<TContainerBuilder>): HostBuilder

    fun build(): Host
}
