package net.rikarin.options

import net.rikarin.configuration.Configuration
import net.rikarin.primitives.ChangeToken

interface OptionsChangeTokenSource<out TOptions> {
    val name: String?

    fun getChangeToken(): ChangeToken
}

class ConfigurationChangeTokenSource<TOptions>(name: String?, private val config: Configuration) : OptionsChangeTokenSource<TOptions> {
    override val name: String?

    constructor(config: Configuration) : this(Options.DEFAULT_NAME, config)

    init {
        this.name = name ?: Options.DEFAULT_NAME
    }

    override fun getChangeToken() = config.reloadToken
}