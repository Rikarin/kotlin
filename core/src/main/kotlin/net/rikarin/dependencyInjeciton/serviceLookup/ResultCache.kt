package net.rikarin.dependencyInjeciton.serviceLookup

import net.rikarin.dependencyInjeciton.ServiceLifetime
import kotlin.reflect.KType

internal class ResultCache(val location: CallSiteResultCacheLocation, val key: ServiceCacheKey) {
    constructor(lifetime: ServiceLifetime, type: KType?, slot: Int) : this(lifetime.toCallSiteResultCacheLocation(),
        ServiceCacheKey(type, slot)
    )

    companion object {
        val NONE = ResultCache(CallSiteResultCacheLocation.NONE, ServiceCacheKey.EMPTY)
    }
}

private fun ServiceLifetime.toCallSiteResultCacheLocation() =
    when (this) {
        ServiceLifetime.SINGLETON -> CallSiteResultCacheLocation.ROOT
        ServiceLifetime.SCOPED -> CallSiteResultCacheLocation.SCOPE
        ServiceLifetime.TRANSIENT -> CallSiteResultCacheLocation.DISPOSE
    }