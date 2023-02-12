package net.rikarin.dependencyInjeciton.serviceLookup

import net.rikarin.*
import net.rikarin.dependencyInjeciton.ServiceDescriptor
import net.rikarin.dependencyInjeciton.ServiceProvider
import net.rikarin.dependencyInjeciton.ServiceProviderIsService
import net.rikarin.dependencyInjeciton.ServiceScopeFactory
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.*
import kotlin.reflect.full.createType

private const val DEFAULT_SLOT = 0

internal class CallSiteFactory(
    descriptors: List<ServiceDescriptor>
) : ServiceProviderIsService {
//    private val _stackGuard = StackGuard()
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

            if (serviceType.isGenericTypeDefinition) {
                val implementationType = descriptor.implementationType
                if (implementationType == null || !implementationType.isGenericTypeDefinition) {
                    throw IllegalArgumentException(OPEN_GENERIC_SERVICE_REQUIRES_OPEN_GENERIC_IMPLEMENTATION.format(serviceType))
                }

                if (implementationType.isAbstract) { // TODO: isInterface
                    throw IllegalArgumentException(TYPE_CANNOT_BE_ACTIVATED.format(implementationType, serviceType))
                }

                // TODO
            } else if (descriptor.implementationInstance == null && descriptor.implementationFactory == null) {
                assert(descriptor.implementationType != null)
                val implementationType = descriptor.implementationType!!

                if (implementationType.isGenericTypeDefinition ||
                    implementationType.isAbstract
                ) { // TODO: isInterface
                    throw IllegalArgumentException(TYPE_CANNOT_BE_ACTIVATED.format(implementationType, serviceType))
                }
            }

            val cacheItem = _descriptorLookup.getOrDefault(serviceType, ServiceDescriptorCacheItem())
            _descriptorLookup[serviceType] = cacheItem.add(descriptor)
        }
    }

    internal fun getCallSite(serviceDescriptor: ServiceDescriptor, callSiteChain: CallSiteChain): ServiceCallSite? {
        val descriptor = _descriptorLookup[serviceDescriptor.serviceType]
        if (descriptor != null) {
            return tryCreateExact(serviceDescriptor, serviceDescriptor.serviceType, callSiteChain, descriptor.getSlot(serviceDescriptor))
        }

        println("_descriptorLookup didn't contain requested serviceDescriptor")
        return null
    }

    internal fun getCallSite(serviceType: KType, callSiteChain: CallSiteChain) =
        _callSiteCache.getOrElse(ServiceCacheKey(serviceType, DEFAULT_SLOT)) { createCallSite(serviceType, callSiteChain) }

    private fun createCallSite(serviceType: KType, callSiteChain: CallSiteChain): ServiceCallSite? {
        // TODO: stack guard

        val callSiteLock = _callSiteLocks.getOrPut(serviceType) { Any() }
        synchronized(callSiteLock) {
            callSiteChain.checkCircularDependency(serviceType)
        }

        return tryCreateExact(serviceType, callSiteChain) ?:
            tryCreateOpenGeneric(serviceType, callSiteChain) ?:
            tryCreateIterable(serviceType, callSiteChain)
    }
    private fun tryCreateExact(serviceType: KType, callSiteChain: CallSiteChain): ServiceCallSite? {
        val descriptor = _descriptorLookup[serviceType]
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

            val lifetime = ResultCache(descriptor.lifetime, serviceType, slot)
            val callSite = if (descriptor.implementationInstance != null) {
                    ConstantCallSite(descriptor.serviceType, descriptor.implementationInstance)
                } else if (descriptor.implementationFactory != null) {
                    FactoryCallSite(lifetime, descriptor.serviceType, descriptor.implementationFactory)
                } else if (descriptor.implementationType != null) {
                    createConstructorCallSite(lifetime, descriptor.serviceType, descriptor.implementationType, callSiteChain)
                } else {
                    throw InvalidOperationException("todo 2")
                }

            _callSiteCache[callSiteKey] = callSite
            return callSite
        }

        return null
    }

    private fun tryCreateOpenGeneric(serviceType: KType, callSiteChain: CallSiteChain): ServiceCallSite? {
        val descriptor = _descriptorLookup[serviceType.getGenericTypeDefinition()]

        if (serviceType.isConstructedGenericType && descriptor != null) {
            return tryCreateOpenGeneric(descriptor.last, serviceType, callSiteChain, DEFAULT_SLOT, true)
        }

        return null
    }

    private fun tryCreateOpenGeneric(
        descriptor: ServiceDescriptor,
        serviceType: KType,
        callSiteChain: CallSiteChain,
        slot: Int,
        throwOnConstraintViolation: Boolean
    ): ServiceCallSite? {
        // TODO
        if (serviceType.isConstructedGenericType && serviceType.classifier == descriptor.serviceType.classifier) {
            val callSiteKey = ServiceCacheKey(serviceType, slot)
            val serviceCallSite = _callSiteCache[callSiteKey]
            if (serviceCallSite != null) {
                return serviceCallSite
            }

            assert(descriptor.implementationType != null)
            val lifetime = ResultCache(descriptor.lifetime, serviceType, slot)
            val closedType: KType

            try {
                closedType = descriptor.implementationType!!.asClass().createType(serviceType.arguments)
            } catch (e: Exception) {
                if (throwOnConstraintViolation) {
                    throw e
                }

                return null
            }

            _callSiteCache[callSiteKey] = createConstructorCallSite(lifetime, serviceType, closedType, callSiteChain)
            return _callSiteCache[callSiteKey]
        }

        return null
    }

    private fun tryCreateIterable(serviceType: KType, callSiteChain: CallSiteChain): ServiceCallSite? {
        val callSiteKey = ServiceCacheKey(serviceType, DEFAULT_SLOT)
        val serviceCallSite = _callSiteCache[callSiteKey]
        if (serviceCallSite != null) {
            return serviceCallSite
        }

        try {
            callSiteChain.add(serviceType)

            // TODO: isconstructedGenericType
            if (serviceType.classifier == Iterable::class) { // TODO: not sure about this
                val itemType = serviceType.arguments[0].type!!
                var cacheLocation = CallSiteResultCacheLocation.ROOT
                var callSites = mutableListOf<ServiceCallSite>()

                // constructed generic type
                val descriptors = _descriptorLookup[itemType]
                if (descriptors != null) {
                    for (i in 0 until descriptors.size) {
                        val descriptor = descriptors[i]
                        val slot = descriptors.size - i - 1

                        val callSite = tryCreateExact(descriptor, itemType, callSiteChain, slot)
                        assert(callSite != null)

                        cacheLocation = CallSiteResultCacheLocation.getCommonCacheLocation(cacheLocation, callSite!!.cache.location)
                        callSites.add(callSite)
                    }
                } else {
                    var slot = 0
                    for (i in this.descriptors.size - 1 downTo 0) {
                        val descriptor = this.descriptors[i]
                        val callSite = tryCreateExact(descriptor, itemType, callSiteChain, slot) ?:
                            tryCreateOpenGeneric(descriptor, itemType, callSiteChain, slot, false)

                        if (callSite != null) {
                            slot++

                            cacheLocation = CallSiteResultCacheLocation.getCommonCacheLocation(cacheLocation, callSite.cache.location)
                            callSites.add(callSite)
                        }
                    }

                    callSites.reverse()
                }

                var resultCache = ResultCache.NONE
                if (cacheLocation == CallSiteResultCacheLocation.SCOPE || cacheLocation == CallSiteResultCacheLocation.ROOT) {
                    resultCache = ResultCache(cacheLocation, callSiteKey)
                }

                _callSiteCache[callSiteKey] = IterableCallSite(resultCache, itemType, callSites.toTypedArray())
                return _callSiteCache[callSiteKey]
            }

            return null
        } finally {
            callSiteChain.remove(serviceType)
        }
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

            if (constructors.isEmpty()) {
                throw InvalidOperationException("todo 3")
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

            var parameterCallSites: Array<ServiceCallSite>? = null
            var bestConstructor: KFunction<*>? = null
            var bestConstructorParameterTypes: HashSet<KType>? = null

            for (constructor in constructors.sortedBy { it.parameters.size }.asReversed()) {
                val currentParameterCallSites = createArgumentCallSites(
                    implementationType,
                    callSiteChain,
                    constructor.parameters,
                    false
                )

                if (currentParameterCallSites != null) {
                    if (bestConstructor == null) {
                        bestConstructor = constructor
                        parameterCallSites = currentParameterCallSites
                    } else {
                        if (bestConstructorParameterTypes == null) {
                            bestConstructorParameterTypes = hashSetOf()

                            for (p in bestConstructor.parameters) {
                                bestConstructorParameterTypes.add(p.type)
                            }
                        }

                        for (p in constructor.parameters) {
                            if (!bestConstructorParameterTypes.contains(p.type)) {
                                throw InvalidOperationException(AMBIGUOUS_CONSTRUCTOR_EXCEPTION.format(
                                    implementationType,
                                    bestConstructor,
                                    constructor
                                ))
                            }
                        }
                    }
                }
            }

            if (bestConstructor == null) {
                throw InvalidOperationException(UNABLE_TO_ACTIVATE_TYPE_EXCEPTION.format(implementationType))
            }

            assert(parameterCallSites != null)
            return ConstructorCallSite(lifetime, serviceType, bestConstructor, parameterCallSites!!)
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
        val projected = projectGenericClassArguments(implementationType)

        return parameters.map {
            val type = it.type
            if (type == typeOf<KType>()) {
                return@map ConstantCallSite(type, implementationType)
            }

            val parameterType = replaceGenericArguments(projected, type)
            var callSite = getCallSite(parameterType, callSiteChain)

            if (callSite == null && it.isOptional) {
                callSite = ConstantCallSite(type, null) // TODO
            }

            if (callSite == null) {
                if (throwIfCallSiteNotFound) {
                    throw InvalidOperationException(CANNOT_RESOLVE_SERVICE.format(parameterType, implementationType))
                }

                return null
            }

            callSite
        }.toTypedArray()
    }

    private fun replaceGenericArguments(projected: Map<KType, KType>, type: KType): KType {
        if (type.arguments.isNotEmpty()) {
            val args = mutableListOf<KType>()
            for (argument in type.arguments) {
                args.add(replaceGenericArguments(projected, argument.type!!))
            }

            return type.asClass().createType(args.map { KTypeProjection.invariant(it) })
        }

        return projected[type] ?: type
    }

    private fun projectGenericClassArguments(implementationType: KType): Map<KType, KType> {
        val arguments = implementationType.asClass().typeParameters // T, U
        val projections = implementationType.arguments // BaseClass, BaseClass2

        if (projections.size != arguments.size) {
            throw InvalidOperationException("Arguments and projections doens't match")
        }

        val projected = mutableMapOf<KType, KType>()
        for (i in arguments.indices) {
            projected[arguments[i].createType()] = projections[i].type!! // TODO: not sure about createType()
        }

        return projected
    }

    fun add(type: KType, serviceCallSite: ServiceCallSite) {
        _callSiteCache[ServiceCacheKey(type, DEFAULT_SLOT)] = serviceCallSite
    }

    override fun isService(serviceType: KType): Boolean {
        if (serviceType.isGenericTypeDefinition) { // TODO: check this; not sure it works properly
            return false
        }

        if (_descriptorLookup.containsKey(serviceType)) {
            return true
        }

        // TODO: check for generic type

        return serviceType == typeOf<ServiceProvider>() ||
               serviceType == typeOf<ServiceScopeFactory>() ||
               serviceType == typeOf<ServiceProviderIsService>() || true
    }

    private class ServiceDescriptorCacheItem {
        private var _item: ServiceDescriptor? = null
        private var _items: MutableList<ServiceDescriptor>? = null

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

            throw InvalidOperationException("todo 1")
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

        operator fun get(index: Int): ServiceDescriptor {
            if (index >= size) {
                throw IllegalArgumentException("index is out of range")
            }

            if (index == 0) {
                return _item!!
            }

            return _items!![index - 1]
        }
    }
}