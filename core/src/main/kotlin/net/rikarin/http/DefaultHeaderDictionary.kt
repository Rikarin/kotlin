package net.rikarin.http

import net.rikarin.primitives.StringValues

class DefaultHeaderDictionary : HeaderDictionary() {
    private val _store = mutableMapOf<String, StringValues>()
    override var contentLength
        get() = 53
        set(value) {}

    override val entries get() = _store.entries
    override val keys get() = _store.keys
    override val size get() = _store.size
    override val values get() = _store.values

    override fun clear() = _store.clear()
    override fun containsKey(key: String) = _store.containsKey(key)
    override fun containsValue(value: StringValues) = _store.containsValue(value)
    override fun get(key: String) = _store.get(key)
    override fun isEmpty() = _store.isEmpty()
    override fun put(key: String, value: StringValues) = _store.put(key, value)
    override fun putAll(from: Map<out String, StringValues>) = _store.putAll(from)
    override fun remove(key: String) = _store.remove(key)
    override fun toString() = _store.toString()
}