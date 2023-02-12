package net.rikarin.dependencyInjeciton

import net.rikarin.InvalidOperationException
import net.rikarin.makeGenericType
import kotlin.reflect.KType
import kotlin.reflect.typeOf

inline fun <reified T> ServiceProvider.getService() = getService(typeOf<T>()) as T?
fun ServiceProvider.getRequiredService(type: KType): Any = getService(type) ?: throw InvalidOperationException()
inline fun <reified T : Any> ServiceProvider.getRequiredService(): T = getRequiredService(typeOf<T>()) as T
inline fun <reified T : Any> ServiceProvider.getServices() = getRequiredService<Iterable<T>>()

fun ServiceProvider.getServices(serviceType: KType): Iterable<Any> {
    val genericType = Iterable::class.makeGenericType(serviceType)

    @Suppress("UNCHECKED_CAST")
    return getRequiredService(genericType) as Iterable<Any>
}

fun ServiceProvider.createScope() = getRequiredService<ServiceScopeFactory>().createScope()