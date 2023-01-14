package net.rikarin.http.features

import kotlin.reflect.KType
import kotlin.reflect.full.withNullability

internal class DefaultFeatureCollection() : FeatureCollection {
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

    override fun get(key: KType): Any? {
        val newKey = key.withNullability(false)
        println("get feature by key $key")
        return _features?.getOrDefault(newKey, null) ?: _defaults?.get(newKey)
    }

    override fun set(key: KType, value: Any?) {
        println("set feature by key $key")
        val newKey = key.withNullability(false)

        if (value == null) {
            if (_features?.remove(newKey) != null) {
                _containerRevision++
            }

            return
        }

        if (_features == null) {
            _features = mutableMapOf()
        }

        _features!![newKey] = value
        _containerRevision++
    }

    override fun iterator(): Iterator<Pair<KType, Any?>> {
//        return _features?.iterator() ?: _defaults.iterator()
        TODO("Not yet implemented")
    }
}