package net.rikarin.dependencyInjeciton.serviceLookup

internal enum class RuntimeResolverLock(val id: Int) {
    Scope(1), Root(2);
}