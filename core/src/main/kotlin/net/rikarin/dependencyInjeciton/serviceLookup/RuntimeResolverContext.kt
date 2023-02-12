package net.rikarin.dependencyInjeciton.serviceLookup

internal class RuntimeResolverContext {
    lateinit var scope: ServiceProviderEngineScope
    lateinit var acquiredLocks: RuntimeResolverLock
}