package net.rikarin.http

import net.rikarin.dependencyInjeciton.ServiceProvider
import net.rikarin.http.features.FeatureCollection

typealias RequestDelegate = suspend (HttpContext) -> Unit

interface ApplicationBuilder {
    var applicationServices: ServiceProvider
    val serverFeatures: FeatureCollection
    val properties: MutableMap<String, Any?>

    fun use(middleware: (RequestDelegate) -> RequestDelegate): ApplicationBuilder
    fun new(): ApplicationBuilder
    fun build(): RequestDelegate
}