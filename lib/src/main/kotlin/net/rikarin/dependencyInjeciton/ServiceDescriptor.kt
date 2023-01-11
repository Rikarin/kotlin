package net.rikarin.dependencyInjeciton

import kotlin.reflect.KType

typealias ServiceImplementationFactory = (serviceProvider: ServiceProvider) -> Any

data class ServiceDescriptor(
    val serviceType: KType,
    val lifetime: ServiceLifetime,
    val implementationType: KType? = null,
    val implementationFactory: ServiceImplementationFactory? = null,
    val implementationInstance: Any? = null
)