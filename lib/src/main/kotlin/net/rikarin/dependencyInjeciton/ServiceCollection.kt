package net.rikarin.dependencyInjeciton

import kotlin.reflect.KClass

interface ServiceCollection : MutableList<ServiceDescriptor> {
    fun buildServiceProvider(): ServiceProvider {
        val implementations: MutableMap<KClass<*>, MutableList<ServiceDescriptor>> = mutableMapOf()
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
inline fun <reified T> ServiceCollection.transient() = transient(T::class)
//inline fun <reified T, reified U> ServiceCollection.transient() = transient(T::class)
inline fun <reified T> ServiceCollection.transient(noinline factory: ServiceImplementationFactory) = transient(T::class, factory)

fun ServiceCollection.transient(type: KClass<*>) = transient(type, type)

fun ServiceCollection.transient(type: KClass<*>, implementation: KClass<*>) =
    add(ServiceDescriptor(type, ServiceLifetime.TRANSIENT, implementationType = implementation))

fun ServiceCollection.transient(type: KClass<*>, factory: ServiceImplementationFactory) =
    add(ServiceDescriptor(type, ServiceLifetime.TRANSIENT, implementationFactory = factory))

// ----------------------------------------------------- SCOPED --------------------------------------------- //
inline fun <reified T> ServiceCollection.scoped() = scoped(T::class)
//inline fun <reified T, reified U> ServiceCollection.scoped() = scoped(T::class)
inline fun <reified T> ServiceCollection.scoped(noinline factory: ServiceImplementationFactory) = scoped(T::class, factory)

fun ServiceCollection.scoped(type: KClass<*>) = scoped(type, type)

fun ServiceCollection.scoped(type: KClass<*>, implementation: KClass<*>) =
    add(ServiceDescriptor(type, ServiceLifetime.SCOPED, implementationType = implementation))

fun ServiceCollection.scoped(type: KClass<*>, factory: ServiceImplementationFactory) =
    add(ServiceDescriptor(type, ServiceLifetime.SCOPED, implementationFactory = factory))

// ----------------------------------------------------- SINGLETON ------------------------------------------ //
inline fun <reified T> ServiceCollection.singleton() = singleton(T::class)
//inline fun <reified T, reified U> ServiceCollection.singleton() = singleton(T::class)
inline fun <reified T> ServiceCollection.singleton(noinline factory: ServiceImplementationFactory) = singleton(T::class, factory)

fun ServiceCollection.singleton(type: KClass<*>) = singleton(type, type)

fun ServiceCollection.singleton(type: KClass<*>, implementation: KClass<*>) =
    add(ServiceDescriptor(type, ServiceLifetime.SINGLETON, implementationType = implementation))

fun ServiceCollection.singleton(type: KClass<*>, factory: ServiceImplementationFactory) =
    add(ServiceDescriptor(type, ServiceLifetime.SINGLETON, implementationFactory = factory))

// these are valid only on singleton
inline fun <reified T> ServiceCollection.singleton(instance: Any) = singleton(T::class, instance)
fun ServiceCollection.singleton(type: KClass<*>, instance: Any) =
    add(ServiceDescriptor(type, ServiceLifetime.SINGLETON, implementationInstance = instance))
