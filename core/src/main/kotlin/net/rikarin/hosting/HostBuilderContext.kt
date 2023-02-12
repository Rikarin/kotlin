package net.rikarin.hosting

import net.rikarin.configuration.Configuration

class HostBuilderContext(val properties: MutableMap<Any, Any>) {
    lateinit var hostingEnvironment: HostEnvironment
    lateinit var configuration: Configuration
}