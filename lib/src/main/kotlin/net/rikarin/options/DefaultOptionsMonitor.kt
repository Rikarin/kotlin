package net.rikarin.options

import net.rikarin.Disposable

class DefaultOptionsMonitor<TOptions : Any> : OptionsMonitor<TOptions>, Disposable {
    // TODO: finish this class
    override val currentValue: TOptions
        get() = TODO("Not yet implemented")

    override fun get(name: String?): TOptions {
        TODO("Not yet implemented")
    }

    override fun onChange(listener: (TOptions, String?) -> Unit): Disposable {
        TODO("Not yet implemented")
    }

    override fun close() {
        TODO("Not yet implemented")
    }
}