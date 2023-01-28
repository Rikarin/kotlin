package net.rikarin.dependencyInjeciton.serviceLookup

import kotlin.reflect.KType
import kotlin.reflect.KTypeProjection
import kotlin.reflect.full.createType

internal class IterableCallSite(
    cache: ResultCache,
    val itemType: KType,
    val serviceCallSites: Array<ServiceCallSite>
) : ServiceCallSite(cache) {
    override val kind = CallSiteKind.ITERABLE
    override val serviceType
        get() = Iterable::class.createType(listOf(KTypeProjection.invariant(itemType)))

    override val implementationType
        get() = Array::class.createType(listOf(KTypeProjection.invariant(itemType)))
}