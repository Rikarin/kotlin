package net.rikarin.dependencyInjeciton

import kotlin.reflect.KType
import kotlin.reflect.typeOf

// ----------------------------------------------------- TRANSIENT ------------------------------------------ //
inline fun <reified T, reified U> ServiceCollection.addTransient() = addTransient(typeOf<T>(), typeOf<U>())
inline fun <reified T> ServiceCollection.addTransient(noinline factory: ServiceImplementationFactory) =
    this.addTransient(typeOf<T>(), factory)

fun ServiceCollection.addTransient(type: KType) = this.addTransient(type, type)

fun ServiceCollection.addTransient(type: KType, implementation: KType) =
    add(ServiceDescriptor(type, ServiceLifetime.TRANSIENT, implementationType = implementation))

fun ServiceCollection.addTransient(type: KType, factory: ServiceImplementationFactory) =
    add(ServiceDescriptor(type, ServiceLifetime.TRANSIENT, implementationFactory = factory))

// ----------------------------------------------------- SCOPED --------------------------------------------- //
inline fun <reified T, reified U> ServiceCollection.addScoped() = addScoped(typeOf<T>(), typeOf<U>())
inline fun <reified T> ServiceCollection.addScoped(noinline factory: ServiceImplementationFactory) = addScoped(typeOf<T>(), factory)

fun ServiceCollection.addScoped(type: KType) = addScoped(type, type)

fun ServiceCollection.addScoped(type: KType, implementation: KType) =
    add(ServiceDescriptor(type, ServiceLifetime.SCOPED, implementationType = implementation))

fun ServiceCollection.addScoped(type: KType, factory: ServiceImplementationFactory) =
    add(ServiceDescriptor(type, ServiceLifetime.SCOPED, implementationFactory = factory))

// ----------------------------------------------------- SINGLETON ------------------------------------------ //
inline fun <reified T, reified U> ServiceCollection.addSingleton() = addSingleton(typeOf<T>(), typeOf<U>())
inline fun <reified T> ServiceCollection.addSingleton(noinline factory: ServiceImplementationFactory) = addSingleton(typeOf<T>(), factory)

fun ServiceCollection.addSingleton(type: KType) = addSingleton(type, type)

fun ServiceCollection.addSingleton(type: KType, implementation: KType) =
    add(ServiceDescriptor(type, ServiceLifetime.SINGLETON, implementationType = implementation))

fun ServiceCollection.addSingleton(type: KType, factory: ServiceImplementationFactory) =
    add(ServiceDescriptor(type, ServiceLifetime.SINGLETON, implementationFactory = factory))

// these are valid only on singleton
inline fun <reified T> ServiceCollection.addSingleton(instance: Any) {
    addSingleton(typeOf<T>(), instance)
}

fun ServiceCollection.addSingleton(type: KType, instance: Any) =
    add(ServiceDescriptor(type, ServiceLifetime.SINGLETON, implementationInstance = instance))
