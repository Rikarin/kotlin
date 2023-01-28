package net.rikarin.options

import java.util.concurrent.ConcurrentHashMap

class OptionsCache<TOptions : Any> : OptionsMonitorCache<TOptions> {
    private val _cache = ConcurrentHashMap<String, TOptions>(31)

    override fun getOrAdd(name: String?, createOptions: () -> TOptions): TOptions {
        TODO("Not yet implemented")
    }

    override fun tryAdd(name: String?, options: TOptions): Boolean {
        _cache[name ?: Options.DEFAULT_NAME] = options
        return true // TODO: improve this
    }

    override fun tryRemove(name: String?) = _cache.remove(name ?: Options.DEFAULT_NAME) != null
    override fun clear() = _cache.clear()
}