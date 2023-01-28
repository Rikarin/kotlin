package net.rikarin.dependencyInjeciton

import kotlin.reflect.KType

fun ServiceCollection.buildServiceProvider(): ServiceProvider {
    // TODO: change this

    val implementations: MutableMap<KType, MutableList<ServiceDescriptor>> = mutableMapOf()
    for (desc in this) {
        if (implementations[desc.serviceType] == null) {
            implementations[desc.serviceType] = mutableListOf(desc)
        } else {
            implementations[desc.serviceType]!!.add(desc)
        }
    }

    return DefaultServiceProvider(implementations, mutableMapOf(), null)
}