package net.rikarin.dependencyInjeciton

import kotlin.reflect.KClass

interface ServiceProvider : AutoCloseable {
    fun getService(type: KClass<*>): Any?
    fun getServices(type: KClass<*>): Collection<Any>
    fun getRequiredService(type: KClass<*>): Any

    fun createScope(): ServiceScope
}

inline fun <reified T> ServiceProvider.getService() = getService(T::class) as T?
inline fun <reified T> ServiceProvider.getServices() = getServices(T::class) as Collection<T>
inline fun <reified T> ServiceProvider.getRequiredService() = getRequiredService(T::class) as T

