package net.rikarin.dependencyInjeciton.serviceLookup

import net.rikarin.CIRCULAR_DEPENDENCY
import net.rikarin.InvalidOperationException
import kotlin.reflect.KType

class CallSiteChain {
    private val _callSiteChain = mutableMapOf<KType, ChainItemInfo>()

    fun checkCircularDependency(serviceType: KType) {
        if (_callSiteChain.containsKey(serviceType)) {
            throw InvalidOperationException(createCircularDependencyExceptionMessage(serviceType))
        }
    }

    fun remove(serviceType: KType) {
        _callSiteChain.remove(serviceType)
    }

    fun add(serviceType: KType, implementationType: KType? = null) {
        _callSiteChain[serviceType] = ChainItemInfo(_callSiteChain.size, implementationType)
    }

    override fun toString() = _callSiteChain.toString()

    private data class ChainItemInfo(val order: Int, val implementationType: KType?)

    private fun createCircularDependencyExceptionMessage(type: KType): String {
        val messageBuilder = StringBuilder()
        messageBuilder.append(CIRCULAR_DEPENDENCY.format(type))
        messageBuilder.appendLine()

        appendResolutionPath(messageBuilder,type)
        return messageBuilder.toString()
    }

    private fun appendResolutionPath(builder: StringBuilder, currentlyResolving: KType) {
        val ordered = _callSiteChain.toList().sortedBy { it.second.order }

        for (pair in ordered) {
            val serviceType = pair.first
            val implementationType = pair.second.implementationType
            if (implementationType == null || serviceType == implementationType) {
                builder.append(serviceType.toString())
            } else {
                builder.append("%s(%s)".format(serviceType, implementationType))
            }

            builder.append(" -> ")
        }

        builder.append(currentlyResolving)
    }
}