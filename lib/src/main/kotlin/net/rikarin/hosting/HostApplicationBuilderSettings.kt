package net.rikarin.hosting

import net.rikarin.configuration.ConfigurationManager


// Consider using data class
//class HostApplicationBuilderSettings {
//    var disableDefaults: Boolean = false
//    var args: Array<String>? = null
//    var configuration: ConfigurationManager? = null
//    var environmentName: String? = null
//    var applicationName: String? = null
//    var contentRootPath: String? = null
//}

data class HostApplicationBuilderSettings(
    var disableDefaults: Boolean = false,
    var args: Array<String>? = null,
    var configuration: ConfigurationManager? = null,
    var environmentName: String? = null,
    var applicationName: String? = null,
    var contentRootPath: String? = null
)
