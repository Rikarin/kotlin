package net.rikarin.options

import net.rikarin.dependencyInjeciton.ServiceCollection
import net.rikarin.dependencyInjeciton.addSingleton

private const val DEFAULT_VALIDATION_FAILURE_MESSAGE = "A validation error has occurred."

class OptionsBuilder<TOptions : Any>(val services: ServiceCollection, name: String?) {
    val name: String

    init {
        this.name = name ?: Options.DEFAULT_NAME
    }

    fun configure(configureOptions: (TOptions) -> Unit): OptionsBuilder<TOptions> {
        services.addSingleton<ConfigureOptions<TOptions>>(DefaultConfigureNamedOptions(name, configureOptions))
        return this
    }

    fun postConfigure(configureOptions: (TOptions) -> Unit): OptionsBuilder<TOptions> {
        services.addSingleton<PostConfigureOptions<TOptions>>(DefaultPostConfigureOptions(name, configureOptions))
        return this
    }

    fun validate(validation: (TOptions) -> Boolean) = validate(validation, DEFAULT_VALIDATION_FAILURE_MESSAGE)
    fun validate(validation: (TOptions) -> Boolean, failureMessage: String): OptionsBuilder<TOptions> {
        services.addSingleton<ValidateOptions<TOptions>>(DefaultValidateOptions(name, validation, failureMessage))
        return this
    }
}