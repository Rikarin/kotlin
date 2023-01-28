package net.rikarin.dependencyInjeciton.serviceLookup

import net.rikarin.dependencyInjeciton.ServiceProviderIsService
import kotlin.reflect.KType

internal class CallSiteFactory : ServiceProviderIsService {
    override fun isService(serviceType: KType): Boolean {
        TODO("Not yet implemented")
    }

}