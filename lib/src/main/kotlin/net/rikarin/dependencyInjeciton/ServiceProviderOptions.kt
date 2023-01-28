package net.rikarin.dependencyInjeciton

class ServiceProviderOptions {
    var validateScopes = false
    var validateOnBuild = false

    companion object {
        internal val DEFAULT = ServiceProviderOptions()
    }
}