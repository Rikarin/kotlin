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


class DefaultFeatureCollection() : FeatureCollection {
    private var _features: MutableMap<KType, Any>? = null
    private var _defaults: FeatureCollection? = null
    private var _containerRevision = 0

    constructor(defaults: FeatureCollection) : this() {
        _defaults = defaults
    }

    override val isReadOnly: Boolean
        get() = false
    override val revision: Int
        get() = _containerRevision + (_defaults?.revision ?: 0)

    override fun get(key: KType) = _features?.getOrDefault(key, null) ?: _defaults?.get(key)

    override fun set(key: KType, value: Any?) {
        if (value == null) {
            if (_features?.remove(key) != null) {
                _containerRevision++
            }

            return
        }

        if (_features == null) {
            _features = mutableMapOf()
        }

        _features!![key] = value
        _containerRevision++
    }

    override fun iterator(): Iterator<Pair<KType, Any?>> {
//        return _features?.iterator() ?: _defaults.iterator()
        TODO("Not yet implemented")
    }
}
