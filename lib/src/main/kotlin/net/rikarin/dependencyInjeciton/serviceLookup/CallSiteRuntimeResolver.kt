package net.rikarin.dependencyInjeciton.serviceLookup

import net.rikarin.dependencyInjeciton.Inject
import net.rikarin.dependencyInjeciton.ServiceProvider
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.typeOf

internal object CallSiteRuntimeResolver : CallSiteVisitor<RuntimeResolverContext, Any?>() {
    fun resolve(callSite: ServiceCallSite, scope: ServiceProviderEngineScope): Any? {
        if (scope.isRootScope && callSite.value is Any) {
            return callSite.value
        }

        return visitCallSite(callSite, RuntimeResolverContext().apply {
            this.scope = scope
        })
    }

    override fun visitDisposeCache(callSite: ServiceCallSite, argument: RuntimeResolverContext): Any? {
        return argument.scope.captureDisposable(visitCallSiteMain(callSite, argument))
    }

    override fun visitConstructor(callSite: ConstructorCallSite, argument: RuntimeResolverContext): Any? {
        val parameterValues = if (callSite.parameterCallSites.isEmpty()) listOf()
            else callSite.parameterCallSites.map { visitCallSite(it, argument) }

        return injectServiceProvider(callSite.constructor.call(*parameterValues.toTypedArray()), argument.scope)
    }

    override fun visitRootCache(callSite: ServiceCallSite, argument: RuntimeResolverContext): Any? {
        if (callSite.value is Any) {
            return callSite.value
        }

        val serviceProviderEngine = argument.scope.rootProvider.root
        synchronized(callSite) {
            // Lock the callsite and check if another thread already cached the value
            if (callSite.value is Any) {
                return callSite.value
            }

            val resolved = visitCallSiteMain(callSite, RuntimeResolverContext().apply {
                scope = serviceProviderEngine
                // TODO
//                val lockType = RuntimeResolverLock.Root
//                acquiredLocks = argument.acquiredLocks | lockType
            })

            serviceProviderEngine.captureDisposable(resolved)
            callSite.value = resolved
            return resolved
        }
    }

    override fun visitScopeCache(callSite: ServiceCallSite, argument: RuntimeResolverContext): Any? {
        return if (argument.scope.isRootScope) visitRootCache(callSite, argument)
            else visitCache(callSite, argument, argument.scope, RuntimeResolverLock.Scope)
    }

    override fun visitConstant(callSite: ConstantCallSite, argument: RuntimeResolverContext): Any? = callSite.value

    override fun visitServiceProvider(callSite: ServiceProviderCallSite, argument: RuntimeResolverContext): Any? =
        argument.scope

    override fun visitIterable(callSite: IterableCallSite, argument: RuntimeResolverContext): Any? =
        callSite.serviceCallSites.map { visitCallSite(it, argument) }.toTypedArray()

    override fun visitFactory(callSite: FactoryCallSite, argument: RuntimeResolverContext): Any? =
        injectServiceProvider(callSite.factory(argument.scope), argument.scope)

    private fun visitCache(
        callSite: ServiceCallSite,
        context: RuntimeResolverContext,
        serviceProviderEngine: ServiceProviderEngineScope,
        lockType: RuntimeResolverLock
    ): Any? {

        // TODO: locks and stuff

        val resolvedServices = serviceProviderEngine.resolvedServices

        try {
            var resolved = resolvedServices.getOrDefault(callSite.cache.key, null)
            if (resolved != null) {
                return resolved
            }

            resolved = visitCallSiteMain(callSite, RuntimeResolverContext().apply {
                scope = serviceProviderEngine
                // TODO
//                acquiredLocks
            })

            serviceProviderEngine.captureDisposable(resolved)
            resolvedServices[callSite.cache.key] = resolved
            return resolved
        } finally {
            // TODO
        }
    }

    private fun injectServiceProvider(instance: Any?, scope: ServiceProvider): Any? {
        if (instance == null) {
            return instance
        }

        val props = instance::class.memberProperties.filter {
            it.hasAnnotation<Inject>() && it.returnType == typeOf<ServiceProvider?>()
        }

        for (prop in props) {
            if (prop is KMutableProperty<*>) {
                prop.isAccessible = true
                prop.setter.call(instance, scope)
            }
        }

        return instance
    }
}