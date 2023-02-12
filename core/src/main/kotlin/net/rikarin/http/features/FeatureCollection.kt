package net.rikarin.http.features

import kotlin.reflect.KType
import kotlin.reflect.typeOf

interface FeatureCollection : Iterable<Pair<KType, Any?>> {
    val isReadOnly: Boolean
    val revision: Int

    operator fun get(key: KType): Any?
    operator fun set(key: KType, value: Any?)
}

inline fun <reified TFeature> FeatureCollection.get() = this[typeOf<TFeature>()] as TFeature
inline fun <reified TFeature> FeatureCollection.set(instance: TFeature?) {
    this[typeOf<TFeature>()] = instance
}
