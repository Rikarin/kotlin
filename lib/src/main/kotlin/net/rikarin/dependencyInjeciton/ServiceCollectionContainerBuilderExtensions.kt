package net.rikarin.dependencyInjeciton

fun ServiceCollection.buildServiceProvider() = buildServiceProvider(ServiceProviderOptions.DEFAULT)

fun ServiceCollection.buildServiceProvider(validateScope: Boolean) =
    buildServiceProvider(ServiceProviderOptions().apply { validateScopes = validateScope })

fun ServiceCollection.buildServiceProvider(options: ServiceProviderOptions) =
    DefaultServiceProvider(this, options)
