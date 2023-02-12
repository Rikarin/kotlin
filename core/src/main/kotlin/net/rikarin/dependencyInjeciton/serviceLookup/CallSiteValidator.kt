package net.rikarin.dependencyInjeciton.serviceLookup

import net.rikarin.DIRECT_SCOPED_RESOLVED_FROM_ROOT
import net.rikarin.InvalidOperationException
import net.rikarin.SCOPED_RESOLVED_FROM_ROOT
import net.rikarin.dependencyInjeciton.ServiceLifetime
import net.rikarin.dependencyInjeciton.ServiceScope
import net.rikarin.dependencyInjeciton.ServiceScopeFactory
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KType
import kotlin.reflect.typeOf

internal class CallSiteValidator : CallSiteVisitor<CallSiteValidator.CallSiteValidatorState, KType?>() {
    private val _scopedServices = ConcurrentHashMap<KType, KType>()

    fun validateCallSite(callSite: ServiceCallSite) {
        val scoped = visitCallSite(callSite, CallSiteValidatorState())
        if (scoped != null) {
            _scopedServices[callSite.serviceType] = scoped
        }
    }

    fun validateResolution(serviceType: KType, scope: ServiceScope, rootScope: ServiceScope) {
        val scopedService = _scopedServices[serviceType]
        if (scope == rootScope && scopedService != null) {
            if (serviceType == scopedService) {
                throw InvalidOperationException(DIRECT_SCOPED_RESOLVED_FROM_ROOT.format(ServiceLifetime.SCOPED, serviceType))
            }

            throw InvalidOperationException(SCOPED_RESOLVED_FROM_ROOT.format(serviceType, ServiceLifetime.SCOPED, scopedService))
        }
    }

    override fun visitConstructor(callSite: ConstructorCallSite, argument: CallSiteValidatorState): KType? {
        var result: KType? = null

        for (cs in callSite.parameterCallSites) {
            val scoped = visitCallSite(cs, argument)
            if (result == null) {
                result = scoped
            }
        }

        return result
    }

    override fun visitIterable(callSite: IterableCallSite, argument: CallSiteValidatorState): KType? {
        var result: KType? = null

        for (cs in callSite.serviceCallSites) {
            val scoped = visitCallSite(cs, argument)
            if (result == null) {
                result = scoped
            }
        }

        return result
    }

    override fun visitRootCache(callSite: ServiceCallSite, argument: CallSiteValidatorState): KType? {
        argument.singleton = callSite
        return visitCallSiteMain(callSite, argument)
    }

    override fun visitScopeCache(callSite: ServiceCallSite, argument: CallSiteValidatorState): KType? {
        if (callSite.serviceType == typeOf<ServiceScopeFactory>()) {
            return null
        }

        if (argument.singleton != null) {
            throw InvalidOperationException()
        }

        visitCallSiteMain(callSite, argument)
        return callSite.serviceType
    }

    override fun visitConstant(callSite: ConstantCallSite, argument: CallSiteValidatorState) = null
    override fun visitServiceProvider(callSite: ServiceProviderCallSite, argument: CallSiteValidatorState) = null
    override fun visitFactory(callSite: FactoryCallSite, argument: CallSiteValidatorState) = null

    internal class CallSiteValidatorState {
        var singleton: ServiceCallSite? = null
    }
}