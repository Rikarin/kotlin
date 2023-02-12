package net.rikarin.dependencyInjeciton

data class ServiceProviderOptions(val validateScopes: Boolean = false, val validateOnBuild: Boolean = false) {
    companion object {
        internal val DEFAULT = ServiceProviderOptions()
    }
}