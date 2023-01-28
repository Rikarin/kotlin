package net.rikarin.dependencyInjeciton.serviceLookup

import net.rikarin.Disposable
import kotlin.reflect.KType
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.typeOf

internal abstract class ServiceCallSite(val cache: ResultCache) {
    abstract val serviceType: KType
    abstract val implementationType: KType?
    abstract val kind: CallSiteKind
    var value: Any? = null

    val captureDisposable
        get() = implementationType == null || implementationType!!.isSubtypeOf(typeOf<Disposable>())
}