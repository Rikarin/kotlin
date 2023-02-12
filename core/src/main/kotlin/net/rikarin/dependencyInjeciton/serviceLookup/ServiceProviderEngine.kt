package net.rikarin.dependencyInjeciton.serviceLookup

internal abstract class ServiceProviderEngine {
    abstract fun realizeService(callSite: ServiceCallSite): (ServiceProviderEngineScope) -> Any?
}