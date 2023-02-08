package net.rikarin.options

import net.rikarin.configuration.BinderOptionsAction
import net.rikarin.configuration.Configuration

class NamedConfigureFromConfigurationOptions<TOptions : Any>(
    name: String?,
    config: Configuration,
    configureBinder: BinderOptionsAction?
) : DefaultConfigureNamedOptions<TOptions>(name, { options -> config.bind(options, configureBinder) }) {
    constructor(name: String?, config: Configuration) : this(name, config, { })
}