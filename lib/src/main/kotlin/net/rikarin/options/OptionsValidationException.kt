package net.rikarin.options

import kotlin.reflect.KType

class OptionsValidationException(val optionsName: String, val optionsType: KType, messages: Collection<String>?) : Exception() {
    override val message: String
        get() = failures.joinToString("; ")

    var failures: Collection<String>
        private set

    init {
        failures = messages ?: listOf()
    }
}