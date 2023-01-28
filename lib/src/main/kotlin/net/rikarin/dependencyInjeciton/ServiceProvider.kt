package net.rikarin.dependencyInjeciton

import net.rikarin.Disposable
import kotlin.reflect.KType
import kotlin.reflect.typeOf

interface ServiceProvider : Disposable {
    fun getService(type: KType): Any?
//    fun getServices(type: KType): Collection<Any>
//    fun getRequiredService(type: KType): Any

//    fun createScope(): ServiceScope
}

inline fun <reified T> ServiceProvider.getService() = getService(typeOf<T>()) as T?
inline fun <reified T> ServiceProvider.getServices() = getServices(typeOf<T>()) as Collection<T>
inline fun <reified T> ServiceProvider.getRequiredService() = getRequiredService(typeOf<T>()) as T
