package net.rikarin

import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.KTypeProjection
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.createType
import kotlin.reflect.full.starProjectedType

inline fun <reified T> nameOf() = T::class.simpleName

fun KClass<*>.makeGenericType(vararg typeArguments: KType) =
    this.createType(typeArguments.map { KTypeProjection.invariant(it) })

fun KType.asClass() = classifier as KClass<*>
fun KType.createInstance() = this.asClass().createInstance()

val KType.isGenericTypeDefinition
    get() = arguments.contains(KTypeProjection.STAR)

val KType.isConstructedGenericType
    get() = arguments.isNotEmpty() && !arguments.contains(KTypeProjection.STAR)

val KType.isAbstract
    get() = asClass().isAbstract

fun KType.getGenericTypeDefinition() = asClass().starProjectedType
