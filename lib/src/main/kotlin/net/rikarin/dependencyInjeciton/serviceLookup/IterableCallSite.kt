package net.rikarin.dependencyInjeciton.serviceLookup

import net.rikarin.makeGenericType
import kotlin.reflect.KType

internal class IterableCallSite(
    cache: ResultCache,
    val itemType: KType,
    val serviceCallSites: Array<ServiceCallSite>
) : ServiceCallSite(cache) {
    override val kind = CallSiteKind.ITERABLE
    override val serviceType
        get() = Iterable::class.makeGenericType(itemType)

    override val implementationType
        get() = Array::class.makeGenericType(itemType)
}