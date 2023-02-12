package net.rikarin.dependencyInjeciton.serviceLookup

internal abstract class CallSiteVisitor<TArgument, TResult> {
//    private val _stackGuard = StackGuard()

    protected fun visitCallSite(callSite: ServiceCallSite, argument: TArgument): TResult {
        // TODO: stack guard

        return when (callSite.cache.location) {
            CallSiteResultCacheLocation.ROOT -> visitRootCache(callSite, argument)
            CallSiteResultCacheLocation.SCOPE -> visitScopeCache(callSite, argument)
            CallSiteResultCacheLocation.DISPOSE -> visitDisposeCache(callSite, argument)
            CallSiteResultCacheLocation.NONE -> visitNoCache(callSite, argument)
        }
    }

    protected fun visitCallSiteMain(callSite: ServiceCallSite, argument: TArgument): TResult =
        when (callSite.kind) {
            CallSiteKind.FACTORY -> visitFactory(callSite as FactoryCallSite, argument)
            CallSiteKind.CONSTRUCTOR -> visitConstructor(callSite as ConstructorCallSite, argument)
            CallSiteKind.CONSTANT -> visitConstant(callSite as ConstantCallSite, argument)
            CallSiteKind.ITERABLE -> visitIterable(callSite as IterableCallSite, argument)
            CallSiteKind.SERVICE_PROVIDER -> visitServiceProvider(callSite as ServiceProviderCallSite, argument)
        }

    protected fun visitNoCache(callSite: ServiceCallSite, argument: TArgument) = visitCallSiteMain(callSite, argument)
    protected open fun visitDisposeCache(callSite: ServiceCallSite, argument: TArgument) = visitCallSiteMain(callSite, argument)
    protected open fun visitRootCache(callSite: ServiceCallSite, argument: TArgument) = visitCallSiteMain(callSite, argument)
    protected open fun visitScopeCache(callSite: ServiceCallSite, argument: TArgument) = visitCallSiteMain(callSite, argument)

    protected abstract fun visitConstructor(callSite: ConstructorCallSite, argument: TArgument): TResult
    protected abstract fun visitConstant(callSite: ConstantCallSite, argument: TArgument): TResult
    protected abstract fun visitServiceProvider(callSite: ServiceProviderCallSite, argument: TArgument): TResult
    protected abstract fun visitIterable(callSite: IterableCallSite, argument: TArgument): TResult
    protected abstract fun visitFactory(callSite: FactoryCallSite, argument: TArgument): TResult
}