package net.rikarin.dependencyInjeciton.serviceLookup

import kotlin.math.pow
import kotlin.reflect.KType

internal class ServiceCacheKey(val type: KType?, val slot: Int) {
    companion object {
        val EMPTY = ServiceCacheKey(null, 0)
    }

    override fun equals(other: Any?) = other is ServiceCacheKey && type == other.type && slot == other.slot
    override fun hashCode() = ((type?.hashCode() ?: 23) * 397.0).pow(slot).toInt()
}