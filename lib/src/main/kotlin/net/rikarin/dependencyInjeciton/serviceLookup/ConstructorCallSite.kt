package net.rikarin.dependencyInjeciton.serviceLookup

import kotlin.reflect.KFunction
import kotlin.reflect.KType
import kotlin.reflect.full.isSupertypeOf

internal class ConstructorCallSite(
    cache: ResultCache,
    override val serviceType: KType,
    val constructor: KFunction<*>,
    val parameterCallSites: Array<ServiceCallSite>
) : ServiceCallSite(cache) {
    init {
        // TODO: not working for open generics
//        println("se $serviceType ${constructor.returnType}")
//        if (!serviceType.isSupertypeOf(constructor.returnType)) {
//            throw IllegalArgumentException("error")
//        }
    }

    override val kind = CallSiteKind.CONSTRUCTOR
    override val implementationType
        get() = constructor.returnType
}