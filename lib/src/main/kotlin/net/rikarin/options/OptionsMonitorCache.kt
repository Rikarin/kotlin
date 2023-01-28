package net.rikarin.options

interface OptionsMonitorCache<TOptions : Any> {
    fun getOrAdd(name: String?, createOptions: () -> TOptions): TOptions
    fun tryAdd(name: String?, options: TOptions): Boolean
    fun tryRemove(name: String?): Boolean
    fun clear()
}