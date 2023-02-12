package net.rikarin.hosting

import net.rikarin.configuration.ConfigurationManager
import net.rikarin.dependencyInjeciton.DefaultServiceCollection
import net.rikarin.dependencyInjeciton.ServiceCollection

class HostApplicationBuilder(private var settings: HostApplicationBuilderSettings?) {
    val services: ServiceCollection = DefaultServiceCollection()
    val configuration: ConfigurationManager

    constructor(args: Array<String>? = null) : this(HostApplicationBuilderSettings(args = args))

    init {
        if (settings == null) {
            settings = HostApplicationBuilderSettings()
        }

        configuration = settings!!.configuration ?: ConfigurationManager()

        // TODO: finish this
    }


    fun build(): Host {
        TODO()
    }
}
