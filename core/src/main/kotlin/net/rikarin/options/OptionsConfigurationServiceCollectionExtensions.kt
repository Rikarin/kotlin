package net.rikarin.options

import net.rikarin.configuration.BinderOptionsAction
import net.rikarin.configuration.Configuration
import net.rikarin.dependencyInjeciton.ServiceCollection
import net.rikarin.dependencyInjeciton.addSingleton

inline fun <reified TOptions : Any> ServiceCollection.configure(config: Configuration) =
    configure<TOptions>(Options.DEFAULT_NAME, config, { })

inline fun <reified TOptions : Any> ServiceCollection.configure(name: String?, config: Configuration) =
    configure<TOptions>(name, config, { })

inline fun <reified TOptions : Any> ServiceCollection.configure(config: Configuration, noinline configureBinder: BinderOptionsAction?) =
    configure<TOptions>(Options.DEFAULT_NAME, config, configureBinder)

inline fun <reified TOptions : Any> ServiceCollection.configure(name: String?, config: Configuration, noinline configureBinder: BinderOptionsAction?) {
    addOptions()
    addSingleton<OptionsChangeTokenSource<TOptions>>(ConfigurationChangeTokenSource<TOptions>(name, config))
    addSingleton<ConfigureOptions<TOptions>>(NamedConfigureFromConfigurationOptions<TOptions>(name, config, configureBinder))
}