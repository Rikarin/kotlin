package net.rikarin.dependencyInjeciton.serviceLookup

import net.rikarin.dependencyInjeciton.DefaultServiceProvider
import net.rikarin.dependencyInjeciton.ServiceProvider
import kotlin.reflect.typeOf

internal class ServiceProviderCallSite : ServiceCallSite(ResultCache.NONE) {
    override val serviceType = typeOf<ServiceProvider>()
    override val implementationType = typeOf<DefaultServiceProvider>()
    override val kind = CallSiteKind.SERVICE_PROVIDER
}