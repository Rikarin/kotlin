package net.rikarin.dependencyInjeciton

fun ServiceCollection.buildServiceProvider() = buildServiceProvider(ServiceProviderOptions.DEFAULT)

fun ServiceCollection.buildServiceProvider(validateScopes: Boolean) =
    buildServiceProvider(ServiceProviderOptions(validateScopes = validateScopes))

fun ServiceCollection.buildServiceProvider(options: ServiceProviderOptions) =
    DefaultServiceProvider(this, options)
