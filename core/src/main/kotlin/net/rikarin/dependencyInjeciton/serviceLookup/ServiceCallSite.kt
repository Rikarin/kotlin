package net.rikarin.dependencyInjeciton.serviceLookup

import kotlin.reflect.KType

internal abstract class ServiceCallSite(val cache: ResultCache) {
    abstract val serviceType: KType
    abstract val implementationType: KType?
    abstract val kind: CallSiteKind
    var value: Any? = null
}