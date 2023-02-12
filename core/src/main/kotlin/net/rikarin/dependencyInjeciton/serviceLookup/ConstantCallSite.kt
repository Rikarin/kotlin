package net.rikarin.dependencyInjeciton.serviceLookup

import kotlin.reflect.KType
import kotlin.reflect.full.createType

internal class ConstantCallSite(override val serviceType: KType, defaultValue: Any?) : ServiceCallSite(ResultCache.NONE) {
    override val implementationType
        get() = if (value != null) value!!::class.createType() else serviceType

    override val kind = CallSiteKind.CONSTANT

    init {
        value = defaultValue

        // TODO
//        if (defaultValue != null && serviceType.is)
    }
}