package net.rikarin.dependencyInjeciton.serviceLookup

internal enum class CallSiteKind {
    FACTORY, CONSTRUCTOR, CONSTANT, ITERABLE, SERVICE_PROVIDER;
}