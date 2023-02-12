package net.rikarin.options

import net.rikarin.dependencyInjeciton.*

fun ServiceCollection.addOptions(): ServiceCollection {
    tryAddSingleton<Options<*>, UnnamedOptionsManager<*>>()
    tryAddScoped<OptionsSnapshot<*>, OptionsManager<*>>()
    tryAddSingleton<OptionsMonitor<*>, DefaultOptionsMonitor<*>>()
    tryAddTransient<OptionsFactory<*>, DefaultOptionsFactory<*>>()
    tryAddSingleton<OptionsMonitorCache<*>, OptionsCache<*>>()

    return this
}

fun <TOptions : Any> ServiceCollection.configure(configureOptions: (TOptions) -> Unit): ServiceCollection {
    configure(Options.DEFAULT_NAME, configureOptions)
    return this
}

fun <TOptions : Any> ServiceCollection.configure(name: String?, configureOptions: (TOptions) -> Unit): ServiceCollection {
    addOptions()
    addSingleton<ConfigureOptions<TOptions>>(DefaultConfigureNamedOptions(name, configureOptions))

    return this
}

fun <TOptions : Any> ServiceCollection.configureAll(configureOptions: (TOptions) -> Unit): ServiceCollection {
    configure(null, configureOptions)
    return this
}

fun <TOptions : Any> ServiceCollection.postConfigure(configureOptions: (TOptions) -> Unit): ServiceCollection {
    postConfigure(Options.DEFAULT_NAME, configureOptions)
    return this
}

fun <TOptions : Any> ServiceCollection.postConfigure(name: String?, configureOptions: (TOptions) -> Unit): ServiceCollection {
    addOptions()
    addSingleton<PostConfigureOptions<TOptions>>(DefaultPostConfigureOptions(name, configureOptions))

    return this
}

fun <TOptions : Any> ServiceCollection.postConfigureAll(configureOptions: (TOptions) -> Unit): ServiceCollection {
    postConfigure(null, configureOptions)
    return this
}

// TODO: finish ConfigureOptions

fun <TOptions : Any> ServiceCollection.addOptions() = addOptions<TOptions>(Options.DEFAULT_NAME)

fun <TOptions : Any> ServiceCollection.addOptions(name: String?): OptionsBuilder<TOptions> {
    addOptions()
    return OptionsBuilder(this, name)
}