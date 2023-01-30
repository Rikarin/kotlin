package net.rikarin.dependencyInjeciton.serviceLookup

import net.rikarin.InvalidOperationException
import net.rikarin.asClass
import net.rikarin.dependencyInjeciton.ServiceDescriptor
import net.rikarin.dependencyInjeciton.ServiceProvider
import net.rikarin.dependencyInjeciton.ServiceProviderIsService
import net.rikarin.dependencyInjeciton.ServiceScopeFactory
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KParameter
import kotlin.reflect.KType
import kotlin.reflect.typeOf

private const val DEFAULT_SLOT = 0

internal class CallSiteFactory(
    descriptors: List<ServiceDescriptor>
) : ServiceProviderIsService {
    private val _stackGuard = StackGuard()
    private val _callSiteCache = ConcurrentHashMap<ServiceCacheKey, ServiceCallSite>()
    private val _descriptorLookup = mutableMapOf<KType, ServiceDescriptorCacheItem>()
    private val _callSiteLocks = ConcurrentHashMap<KType, Any>()

    internal val descriptors = descriptors.toTypedArray()

    init {
        populate()
    }

    private fun populate() {
        for (descriptor in descriptors) {
            val serviceType = descriptor.serviceType

            // TODO


            val cacheItem = _descriptorLookup.getOrDefault(serviceType, ServiceDescriptorCacheItem())
            _descriptorLookup[serviceType] = cacheItem.add(descriptor)
        }
    }

    // TODO

    internal fun getCallSite(serviceType: KType, callSiteChain: CallSiteChain) =
        _callSiteCache.getOrElse(ServiceCacheKey(serviceType, DEFAULT_SLOT)) { createCallSite(serviceType, callSiteChain) }

    private fun createCallSite(serviceType: KType, callSiteChain: CallSiteChain): ServiceCallSite? {
        // TODO: stack guard

        val callSiteLock = _callSiteLocks.getOrPut(serviceType) { Any() }
        synchronized(callSiteLock) {
            callSiteChain.checkCircularDependency(serviceType)
        }

        return tryCreateExact(serviceType, callSiteChain)
        // TODO: another implementations
    }

    private fun tryCreateExact(serviceType: KType, callSiteChain: CallSiteChain): ServiceCallSite? {
        val descriptor = _descriptorLookup.getOrDefault(serviceType, null)
        if (descriptor != null) {
            return tryCreateExact(descriptor.last, serviceType, callSiteChain, DEFAULT_SLOT)
        }

        return null
    }

    private fun tryCreateExact(
        descriptor: ServiceDescriptor,
        serviceType: KType,
        callSiteChain: CallSiteChain,
        slot: Int
    ): ServiceCallSite? {
        if (serviceType == descriptor.serviceType) {
            val callSiteKey = ServiceCacheKey(serviceType, slot)
            if (_callSiteCache.containsKey(callSiteKey)) {
                return _callSiteCache[callSiteKey]
            }

            val callSite: ServiceCallSite
            val lifetime = ResultCache(descriptor.lifetime, serviceType, slot)
            if (descriptor.implementationInstance != null) {
                callSite = ConstantCallSite(descriptor.serviceType, descriptor.implementationInstance)
            } else if (descriptor.implementationFactory != null) {
                callSite = FactoryCallSite(lifetime, descriptor.serviceType, descriptor.implementationFactory)
            } else if (descriptor.implementationType != null) {
                callSite = createConstructorCallSite(lifetime, descriptor.serviceType, descriptor.implementationType, callSiteChain)
            } else {
                throw InvalidOperationException()
            }

            _callSiteCache[callSiteKey] = callSite
            return callSite
        }

        return null
    }

    private fun createConstructorCallSite(
        lifetime: ResultCache,
        serviceType: KType,
        implementationType: KType,
        callSiteChain: CallSiteChain
    ): ServiceCallSite {
        try {
            callSiteChain.add(serviceType, implementationType)
            val constructors = implementationType.asClass().constructors.toList()

            println("impl ${constructors.size}")
            if (constructors.isEmpty()) {
                throw InvalidOperationException()
            } else if (constructors.size == 1) {
                val constructor = constructors[0]
                val parameters = constructor.parameters

                if (parameters.isEmpty()) {
                    return ConstructorCallSite(lifetime, serviceType, constructor, arrayOf())
                }

                val parameterCallSites = createArgumentCallSites(
                    implementationType,
                    callSiteChain,
                    parameters,
                    true
                )!!

                return ConstructorCallSite(lifetime, serviceType, constructor, parameterCallSites)
            }

            TODO()
        } finally {
            callSiteChain.remove(serviceType)
        }
    }

    private fun createArgumentCallSites(
        implementationType: KType,
        callSiteChain: CallSiteChain,
        parameters: List<KParameter>,
        throwIfCallSiteNotFound: Boolean
    ): Array<ServiceCallSite>? {
        return parameters.map {
            val parameterType = it.type
            var callSite = getCallSite(parameterType, callSiteChain)

            if (callSite == null && it.isOptional) {
                callSite = ConstantCallSite(it.type, null) // TODO
            }

            if (callSite == null) {
                if (throwIfCallSiteNotFound) {
                    throw InvalidOperationException()
                }

                return null
            }

            callSite
        }.toTypedArray()
    }



    fun add(type: KType, serviceCallSite: ServiceCallSite) {
        _callSiteCache[ServiceCacheKey(type, DEFAULT_SLOT)] = serviceCallSite
    }

    override fun isService(serviceType: KType): Boolean {
        // TODO

        if (_descriptorLookup.containsKey(serviceType)) {
            return true
        }

        // TODO

        return serviceType == typeOf<ServiceProvider>() ||
               serviceType == typeOf<ServiceScopeFactory>() ||
               serviceType == typeOf<ServiceProviderIsService>()
    }

    private class ServiceDescriptorCacheItem {
        private var _item: ServiceDescriptor? = null
        private var _items: MutableList<ServiceDescriptor>? = null

        // TODO: this[index]

        val last: ServiceDescriptor
            get() {
                if (_items != null && _items!!.isNotEmpty()) {
                    return _items!!.last()
                }

                assert(_item != null)
                return _item!!
            }

        val size: Int
            get() {
                if (_item == null) {
                    assert(_items == null)
                    return 0
                }

                return 1 + (_items?.size ?: 0)
            }

        fun getSlot(descriptor: ServiceDescriptor): Int {
            if (descriptor == _item) {
                return size - 1
            }

            if (_items != null) {
                val index = _items!!.indexOf(descriptor)
                if (index != -1) {
                    return _items!!.size - (index + 1)
                }
            }

            throw InvalidOperationException()
        }

        fun add(descriptor: ServiceDescriptor): ServiceDescriptorCacheItem {
            val newCacheItem = ServiceDescriptorCacheItem()

            if (_item == null) {
                assert(_items == null)
                newCacheItem._item = descriptor
            } else {
                newCacheItem._item = _item
                newCacheItem._items = _items ?: mutableListOf()
                newCacheItem._items!!.add(descriptor)
            }

            return newCacheItem
        }
    }
}