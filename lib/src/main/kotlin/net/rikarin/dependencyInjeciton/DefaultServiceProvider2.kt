package net.rikarin.dependencyInjeciton

import net.rikarin.AggregateException
import net.rikarin.ObjectDisposedException
import net.rikarin.dependencyInjeciton.serviceLookup.*
import net.rikarin.dependencyInjeciton.serviceLookup.CallSiteValidator
import net.rikarin.dependencyInjeciton.serviceLookup.RuntimeServiceProviderEngine
import net.rikarin.dependencyInjeciton.serviceLookup.ServiceProviderEngine
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KType

private typealias RealizeAction = (ServiceProviderEngineScope) -> Any?

class DefaultServiceProvider2(
    serviceDescriptors: List<ServiceDescriptor>,
    options: ServiceProviderOptions
) : ServiceProvider {
    private val _realizedServices = ConcurrentHashMap<KType, RealizeAction>()
    private val _crateServiceAccessor: () -> Unit = ::createServiceAccessor
    private var _callSiteValidator: CallSiteValidator? = null

    internal var isDisposed = false
        private set

    internal val engine = getEngine()
    internal val root = ServiceProviderEngineScope(this, true)

    // factory

    init {
        // TODO
        // callsite factory

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
    }






    override fun dispose() {
        isDisposed = true
        root.dispose()
    }

    override fun getService(type: KType) = getService(type, root)

    internal fun getService(serviceType: KType,  serviceScope: ServiceProviderEngineScope): Any? {
        ensureNotDisposed()
        TODO()
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

    private fun createServiceAccessor() {
        TODO()
    }

    internal fun replaceServiceAccessor(callSite: ServiceCallSite, accessor: RealizeAction) {
        _realizedServices[callSite.serviceType] = accessor
    }

    private fun validateService(descriptor: ServiceDescriptor) {
        TODO()
    }

    private fun onCreate(callSite: ServiceCallSite) {
        _callSiteValidator?.validateCallSite(callSite)
    }

    private fun onResolve(serviceType: KType, scope: ServiceScope) {
        _callSiteValidator?.validateResolution(serviceType, scope, root)
    }
}