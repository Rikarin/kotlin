package net.rikarin

import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.KTypeProjection
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.createType

inline fun <reified T> nameOf() = T::class.simpleName

fun KClass<*>.makeGenericType(vararg typeArguments: KType) =
    this.createType(typeArguments.map { KTypeProjection.invariant(it) })

fun KType.asClass() = classifier as KClass<*>
fun KType.createInstance() = this.asClass().createInstance()


//fun KType.makeGenericType(vararg typeArguments: KType) =
//    this.createType(typeArguments.map { KTypeProjection.invariant(it) })
