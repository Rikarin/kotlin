package net.rikarin.dependencyInjeciton.serviceLookup

internal object RuntimeServiceProviderEngine : ServiceProviderEngine() {
    override fun realizeService(callSite: ServiceCallSite): (ServiceProviderEngineScope) -> Any? =
        { scope -> CallSiteRuntimeResolver.resolve(callSite, scope) }
}