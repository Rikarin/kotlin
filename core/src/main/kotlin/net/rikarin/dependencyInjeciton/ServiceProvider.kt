package net.rikarin.dependencyInjeciton

import net.rikarin.Disposable
import kotlin.reflect.KType

interface ServiceProvider : Disposable {
    fun getService(type: KType): Any?
}
