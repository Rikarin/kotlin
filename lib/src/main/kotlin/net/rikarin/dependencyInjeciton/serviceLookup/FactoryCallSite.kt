package net.rikarin.dependencyInjeciton.serviceLookup

import net.rikarin.dependencyInjeciton.ServiceProvider
import kotlin.reflect.KType

internal class FactoryCallSite(
    cache: ResultCache,
    override val serviceType: KType,
    val factory: (ServiceProvider) -> Any
) : ServiceCallSite(cache) {
    override val implementationType = null
    override val kind = CallSiteKind.FACTORY
}