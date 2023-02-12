package net.rikarin.dependencyInjeciton

import kotlin.reflect.KType
import kotlin.reflect.typeOf

fun ServiceCollection.tryAdd(descriptor: ServiceDescriptor) {
    if (any { it.serviceType == descriptor.serviceType }) {
        return
    }

    add(descriptor)
}

fun ServiceCollection.tryAdd(descriptors: Collection<ServiceDescriptor>) {
    for (x in descriptors) {
        tryAdd(x)
    }
}


// ======== Transient
fun ServiceCollection.tryAddTransient(service: KType) =
    tryAdd(ServiceDescriptor.transient(service, service))

fun ServiceCollection.tryAddTransient(service: KType, implementation: KType) =
    tryAdd(ServiceDescriptor.transient(service, implementation))

fun ServiceCollection.tryAddTransient(service: KType, factory: ServiceImplementationFactory) =
    tryAdd(ServiceDescriptor.transient(service, factory))

inline fun <reified TService : Any, reified TImplementation : TService> ServiceCollection.tryAddTransient() =
    tryAddTransient(typeOf<TService>(), typeOf<TImplementation>())

inline fun <reified TService : Any> ServiceCollection.tryAddTransient(noinline factory: (ServiceProvider) -> TService) =
    tryAdd(ServiceDescriptor.transient(factory))


// ======== Scoped
fun ServiceCollection.tryAddScoped(service: KType) =
    tryAdd(ServiceDescriptor.scoped(service, service))

fun ServiceCollection.tryAddScoped(service: KType, implementation: KType) =
    tryAdd(ServiceDescriptor.scoped(service, implementation))

fun ServiceCollection.tryAddScoped(service: KType, factory: ServiceImplementationFactory) =
    tryAdd(ServiceDescriptor.scoped(service, factory))

inline fun <reified TService : Any, reified TImplementation : TService> ServiceCollection.tryAddScoped() =
    tryAddScoped(typeOf<TService>(), typeOf<TImplementation>())

inline fun <reified TService : Any> ServiceCollection.tryAddScoped(noinline factory: (ServiceProvider) -> TService) =
    tryAdd(ServiceDescriptor.scoped(factory))


// ========= Singleton
fun ServiceCollection.tryAddSingleton(service: KType) =
    tryAdd(ServiceDescriptor.singleton(service, service))

fun ServiceCollection.tryAddSingleton(service: KType, implementation: KType) =
    tryAdd(ServiceDescriptor.singleton(service, implementation))

fun ServiceCollection.tryAddSingleton(service: KType, factory: ServiceImplementationFactory) =
    tryAdd(ServiceDescriptor.singleton(service, factory))

inline fun <reified TService : Any, reified TImplementation : TService> ServiceCollection.tryAddSingleton() =
    tryAddSingleton(typeOf<TService>(), typeOf<TImplementation>())

inline fun <reified TService : Any> ServiceCollection.tryAddSingleton(noinline factory: (ServiceProvider) -> TService) =
    tryAdd(ServiceDescriptor.singleton(factory))

inline fun <reified TService : Any> ServiceCollection.tryAddSingleton(instance: TService) {
    tryAdd(ServiceDescriptor.singleton(typeOf<TService>(), instance))
}


// TODO: tryAddEnumerable

fun ServiceCollection.replace(descriptor: ServiceDescriptor): ServiceCollection {
    for (i in size downTo 0) {
        val desc = this[i]
        if (desc.serviceType == descriptor.serviceType) {
            removeAt(i)
        }
    }

    add(descriptor)
    return this
}

inline fun <reified T> ServiceCollection.removeAll() = removeAll(typeOf<T>())

fun ServiceCollection.removeAll(type: KType): ServiceCollection {
    for (i in size downTo 0) {
        val desc = this[i]
        if (desc.serviceType == type) {
            removeAt(i)
        }
    }

    return this
}