package net.rikarin.dependencyInjeciton

import kotlin.reflect.KType

interface ServiceProviderIsService {
    fun isService(serviceType: KType): Boolean
}