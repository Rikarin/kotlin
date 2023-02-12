package net.rikarin.dependencyInjeciton

import net.rikarin.AggregateException
import net.rikarin.InvalidOperationException
import net.rikarin.ObjectDisposedException
import net.rikarin.dependencyInjeciton.serviceLookup.*
import net.rikarin.dependencyInjeciton.serviceLookup.CallSiteValidator
import net.rikarin.dependencyInjeciton.serviceLookup.RuntimeServiceProviderEngine
import net.rikarin.dependencyInjeciton.serviceLookup.ServiceProviderEngine
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KType
import kotlin.reflect.typeOf

private typealias RealizeAction = (ServiceProviderEngineScope) -> Any?

class DefaultServiceProvider(
    serviceDescriptors: List<ServiceDescriptor>,
    options: ServiceProviderOptions
) : ServiceProvider {
    private val _realizedServices = ConcurrentHashMap<KType, RealizeAction>()
    private var _callSiteValidator: CallSiteValidator? = null

    internal var isDisposed = false
        private set

    internal val engine = getEngine()
    internal val root = ServiceProviderEngineScope(this, true)
    internal val callSiteFactory = CallSiteFactory(serviceDescriptors)

    init {
        callSiteFactory.add(typeOf<ServiceProvider>(), ServiceProviderCallSite())
        callSiteFactory.add(typeOf<ServiceScopeFactory>(), ConstantCallSite(typeOf<ServiceScopeFactory>(), root))
        callSiteFactory.add(typeOf<ServiceProviderIsService>(), ConstantCallSite(typeOf<ServiceProviderIsService>(), callSiteFactory))

        if (options.validateScopes) {
            _callSiteValidator = CallSiteValidator()
        }

        if (options.validateOnBuild) {
            val exceptions = mutableListOf<Exception>()
            for (desc in serviceDescriptors) {
                try {
                    validateService(desc)
                } catch (e: Exception) {
                    exceptions.add(e)
                }
            }

            if (exceptions.isNotEmpty()) {
                throw AggregateException("Some services are not able to be constructed", *exceptions.toTypedArray())
            }
        }

        // TODO: log event
    }

    override fun dispose() {
        isDisposed = true
        root.dispose()
        // TODO: event log
    }

    override fun getService(type: KType) = getService(type, root)

    internal fun getService(serviceType: KType, serviceScope: ServiceProviderEngineScope): Any? {
        ensureNotDisposed()

        val realizedService = _realizedServices.getOrPut(serviceType) { createServiceAccessor(serviceType) }
        onResolve(serviceType, serviceScope)
        // TODO: event log
        val result = realizedService(serviceScope)

        assert(result == null || callSiteFactory.isService(serviceType))
        return result
    }

    internal fun createScope(): ServiceScope {
        ensureNotDisposed()
        return ServiceProviderEngineScope(this, false)
    }

    private fun getEngine(): ServiceProviderEngine = RuntimeServiceProviderEngine

    private fun ensureNotDisposed() {
        if (isDisposed) {
            throw ObjectDisposedException()
        }
    }

    private fun createServiceAccessor(serviceType: KType): RealizeAction {
        val callSite = callSiteFactory.getCallSite(serviceType, CallSiteChain())

        if (callSite != null) {
            // TODO: log event
            onCreate(callSite)

            if (callSite.cache.location == CallSiteResultCacheLocation.ROOT) {
                val value = CallSiteRuntimeResolver.resolve(callSite, root)
                return { value }
            }

            return engine.realizeService(callSite)
        }

        return { null }
    }

    private fun validateService(descriptor: ServiceDescriptor) {
        // TODO: generic check

        try {
            val callSite = callSiteFactory.getCallSite(descriptor, CallSiteChain())
            if (callSite != null) {
                onCreate(callSite)
            }
        } catch (e: Exception) {
            throw InvalidOperationException("Error while validating the service descriptor '$descriptor': ${e.message}")
        }
    }

    private fun onCreate(callSite: ServiceCallSite) {
        _callSiteValidator?.validateCallSite(callSite)
    }

    private fun onResolve(serviceType: KType, scope: ServiceScope) {
        _callSiteValidator?.validateResolution(serviceType, scope, root)
    }
}