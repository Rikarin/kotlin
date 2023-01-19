package net.rikarin.builder

import net.rikarin.dependencyInjeciton.ServiceProvider
import net.rikarin.http.ApplicationBuilder
import net.rikarin.http.RequestDelegate
import net.rikarin.http.features.DefaultFeatureCollection
import net.rikarin.http.features.FeatureCollection
import net.rikarin.services.ApplicationService

private const val SERVER_FEATURE_KEY = "server.features"
private const val APPLICATION_SERVICES_KEY = "application.services"

internal class DefaultApplicationBuilder: ApplicationBuilder {
    private val _components = mutableListOf<(delegate: RequestDelegate) -> RequestDelegate>()

    override var applicationServices: ServiceProvider
        get() = properties[APPLICATION_SERVICES_KEY] as ServiceProvider
        set(value) {
            properties[APPLICATION_SERVICES_KEY] = value
        }

    override val serverFeatures: FeatureCollection
        get() = properties[SERVER_FEATURE_KEY] as FeatureCollection

    override var properties = mutableMapOf<String, Any?>()
        private set

    constructor(serviceProvider: ServiceProvider) : this(serviceProvider, DefaultFeatureCollection())

    constructor(serviceProvider: ServiceProvider, featureCollection: FeatureCollection) {
        applicationServices = serviceProvider
        properties[SERVER_FEATURE_KEY] = featureCollection
    }

    constructor(app: ApplicationBuilder) {
        properties = app.properties.toMutableMap() // TODO: this should copy original and create new map
    }


    override fun use(middleware: (delegate: RequestDelegate) -> RequestDelegate): ApplicationBuilder {
        _components.add(middleware)
        return this
    }

    override fun new(): ApplicationBuilder {
        return DefaultApplicationBuilder(this)
    }

    override fun build(): RequestDelegate {
        var app: RequestDelegate = {
            // TODO: check stuff
        }

       for (i in _components.size downTo 0) {
           app = _components[i](app)
       }

        return app
    }
}