package net.rikarin.options

import net.rikarin.Disposable

interface OptionsMonitor<out TOptions> {
    val currentValue: TOptions

    fun get(name: String?): TOptions
    fun onChange(listener: (TOptions, String?) -> Unit): Disposable
}

fun <TOptions> OptionsMonitor<TOptions>.onChange(listener: (TOptions) -> Unit): Disposable =
    onChange { options, _ -> listener(options) }