package net.rikarin.dependencyInjeciton

import kotlin.reflect.KType
import kotlin.reflect.typeOf

interface ServiceCollection : MutableList<ServiceDescriptor> {
    fun buildServiceProvider(): ServiceProvider {
        val implementations: MutableMap<KType, MutableList<ServiceDescriptor>> = mutableMapOf()
        for (desc in this) {
            if (implementations[desc.serviceType] == null) {
                implementations[desc.serviceType] = mutableListOf(desc)
            } else {
                implementations[desc.serviceType]!!.add(desc)
            }
        }

        return DefaultServiceProvider(implementations, mutableMapOf(), null)
    }
}

// ----------------------------------------------------- TRANSIENT ------------------------------------------ //
inline fun <reified T, reified U> ServiceCollection.transient() = transient(typeOf<T>(), typeOf<U>())
inline fun <reified T> ServiceCollection.transient(noinline factory: ServiceImplementationFactory) = transient(typeOf<T>(), factory)

fun ServiceCollection.transient(type: KType) = transient(type, type)

fun ServiceCollection.transient(type: KType, implementation: KType) =
    add(ServiceDescriptor(type, ServiceLifetime.TRANSIENT, implementationType = implementation))

fun ServiceCollection.transient(type: KType, factory: ServiceImplementationFactory) =
    add(ServiceDescriptor(type, ServiceLifetime.TRANSIENT, implementationFactory = factory))

// ----------------------------------------------------- SCOPED --------------------------------------------- //
inline fun <reified T, reified U> ServiceCollection.scoped() = scoped(typeOf<T>(), typeOf<U>())
//inline fun <reified T, reified U> ServiceCollection.scoped() = scoped(T::class)
inline fun <reified T> ServiceCollection.scoped(noinline factory: ServiceImplementationFactory) = scoped(typeOf<T>(), factory)

fun ServiceCollection.scoped(type: KType) = scoped(type, type)

fun ServiceCollection.scoped(type: KType, implementation: KType) =
    add(ServiceDescriptor(type, ServiceLifetime.SCOPED, implementationType = implementation))

fun ServiceCollection.scoped(type: KType, factory: ServiceImplementationFactory) =
    add(ServiceDescriptor(type, ServiceLifetime.SCOPED, implementationFactory = factory))

// ----------------------------------------------------- SINGLETON ------------------------------------------ //
inline fun <reified T, reified U> ServiceCollection.singleton() = singleton(typeOf<T>(), typeOf<U>())
inline fun <reified T> ServiceCollection.singleton(noinline factory: ServiceImplementationFactory) = singleton(typeOf<T>(), factory)

fun ServiceCollection.singleton(type: KType) = singleton(type, type)

fun ServiceCollection.singleton(type: KType, implementation: KType) =
    add(ServiceDescriptor(type, ServiceLifetime.SINGLETON, implementationType = implementation))

fun ServiceCollection.singleton(type: KType, factory: ServiceImplementationFactory) =
    add(ServiceDescriptor(type, ServiceLifetime.SINGLETON, implementationFactory = factory))

// these are valid only on singleton
//inline fun <reified T> ServiceCollection.singleton(instance: Any) = singleton(T::class, instance)
inline fun <reified T> ServiceCollection.singleton(instance: Any) {
    singleton(typeOf<T>(), instance)
}

fun ServiceCollection.singleton(type: KType, instance: Any) =
    add(ServiceDescriptor(type, ServiceLifetime.SINGLETON, implementationInstance = instance))
