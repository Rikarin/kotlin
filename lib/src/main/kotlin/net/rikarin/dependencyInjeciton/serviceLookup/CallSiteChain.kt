package net.rikarin.dependencyInjeciton.serviceLookup

import net.rikarin.InvalidOperationException
import kotlin.reflect.KType

class CallSiteChain {
    private val _callSiteChain = mutableMapOf<KType, ChainItemInfo>()

    fun checkCircularDependency(serviceType: KType) {
        if (_callSiteChain.containsKey(serviceType)) {
            // TODO
            throw InvalidOperationException("TODO: add message")
        }
    }

    fun remove(serviceType: KType) {
        _callSiteChain.remove(serviceType)
    }

    fun add(serviceType: KType, implementationType: KType?) {
        _callSiteChain[serviceType] = ChainItemInfo(_callSiteChain.size, implementationType)
    }

    override fun toString() = _callSiteChain.toString()

    private data class ChainItemInfo(val order: Int, val implementationType: KType?)
}