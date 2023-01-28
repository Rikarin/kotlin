package net.rikarin.dependencyInjeciton.serviceLookup

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
        TODO("Not yet implemented")
    }

    override fun visitRootCache(callSite: ServiceCallSite, argument: RuntimeResolverContext): Any? {
        TODO()
    }

    override fun visitScopeCache(callSite: ServiceCallSite, argument: RuntimeResolverContext): Any? {
        return if (argument.scope.isRootScope) visitRootCache(callSite, argument)
            else visitCache(callSite, argument, argument.scope, RuntimeResolverLock.Scope)
    }

    override fun visitConstant(callSite: ConstantCallSite, argument: RuntimeResolverContext): Any? {
        return callSite.value
    }

    override fun visitServiceProvider(callSite: ServiceProviderCallSite, argument: RuntimeResolverContext): Any? {
        return argument.scope
    }

    override fun visitIterable(callSite: IterableCallSite, argument: RuntimeResolverContext): Any? {
        TODO("Not yet implemented")
    }

    override fun visitFactory(callSite: FactoryCallSite, argument: RuntimeResolverContext): Any? {
        return callSite.factory(argument.scope)
    }

    private fun visitCache(
        callSite: ServiceCallSite,
        context: RuntimeResolverContext,
        serviceProviderEngine: ServiceProviderEngineScope,
        lockType: RuntimeResolverLock
    ): Any? {
        TODO()
    }
}