package net.rikarin.dependencyInjeciton

import kotlin.reflect.KType
import kotlin.reflect.typeOf

typealias ServiceImplementationFactory = (ServiceProvider) -> Any

data class ServiceDescriptor(
    val serviceType: KType,
    val lifetime: ServiceLifetime,
    val implementationType: KType? = null,
    val implementationFactory: ServiceImplementationFactory? = null,
    val implementationInstance: Any? = null
) {
    companion object {
        inline fun <reified TService : Any, reified TImplementation : TService> transient() =
            describe<TService, TImplementation>(ServiceLifetime.TRANSIENT)

        fun transient(serviceType: KType, implementationType: KType?) =
            describe(serviceType, implementationType, ServiceLifetime.TRANSIENT)

        inline fun <reified TService : Any, reified TImplementation : TService> transient(noinline implementationFactory: (ServiceProvider) -> TImplementation) =
            describe(typeOf<TService>(), implementationFactory, ServiceLifetime.TRANSIENT)

        fun transient(serviceType: KType, implementationFactory: ServiceImplementationFactory) =
            describe(serviceType, implementationFactory, ServiceLifetime.TRANSIENT)

        // ========= Scoped

        inline fun <reified TService : Any, reified TImplementation : TService> scoped() =
            describe<TService, TImplementation>(ServiceLifetime.SCOPED)

        fun scoped(serviceType: KType, implementationType: KType?) =
            describe(serviceType, implementationType, ServiceLifetime.SCOPED)

        inline fun <reified TService : Any, reified TImplementation : TService> scoped(noinline implementationFactory: (ServiceProvider) -> TImplementation) =
            describe(typeOf<TService>(), implementationFactory, ServiceLifetime.SCOPED)

        fun scoped(serviceType: KType, implementationFactory: ServiceImplementationFactory) =
            describe(serviceType, implementationFactory, ServiceLifetime.SCOPED)

        // ============ Singleton

        inline fun <reified TService: Any, reified TImplementation : TService> singleton() =
            describe<TService, TImplementation>(ServiceLifetime.SINGLETON)

        fun singleton(serviceType: KType, implementationType: KType?) =
            describe(serviceType, implementationType, ServiceLifetime.SINGLETON)

        inline fun <reified TService : Any, reified TImplementation : TService> singleton(noinline implementationFactory: (ServiceProvider) -> TImplementation) =
           describe(typeOf<TService>(), implementationFactory, ServiceLifetime.SINGLETON)

        fun singleton(serviceType: KType, implementationFactory: ServiceImplementationFactory) =
            describe(serviceType, implementationFactory, ServiceLifetime.SINGLETON)

        inline fun <reified TService : Any> singleton(implementationInstance: TService) =
            singleton(typeOf<TService>(), implementationInstance)

        fun singleton(serviceType: KType, implementationInstance: Any?) =
            ServiceDescriptor(serviceType, ServiceLifetime.SINGLETON, implementationInstance = implementationInstance)

        // ======= Describe

        inline fun <reified TService : Any, reified TImplementation : TService> describe(lifetime: ServiceLifetime) =
            describe(typeOf<TService>(), typeOf<TImplementation>(), lifetime)

        fun describe(
            serviceType: KType,
            implementationType: KType?,
            lifetime: ServiceLifetime
        ) = ServiceDescriptor(serviceType, lifetime, implementationType = implementationType)

        fun describe(
            serviceType: KType,
            implementationFactory: ServiceImplementationFactory,
            lifetime: ServiceLifetime
        ) = ServiceDescriptor(serviceType, lifetime, implementationFactory = implementationFactory)
    }
}