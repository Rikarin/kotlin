package net.rikarin.dependencyInjeciton

import kotlin.reflect.KClass

typealias ServiceImplementationFactory = (serviceProvider: ServiceProvider) -> Any

data class ServiceDescriptor(
    val serviceType: KClass<*>,
    val lifetime: ServiceLifetime,
    val implementationType: KClass<*>? = null,
    val implementationFactory: ServiceImplementationFactory? = null,
    val implementationInstance: Any? = null
)