package net.rikarin.http

import net.rikarin.dependencyInjeciton.ServiceProvider
import net.rikarin.http.features.FeatureCollection

typealias RequestDelegate = suspend (context: HttpContext) -> Unit

interface ApplicationBuilder {
    var applicationServices: ServiceProvider
    val serverFeatures: FeatureCollection
    val properties: Map<String, Any?>

    fun use(middleware: (delegate: RequestDelegate) -> RequestDelegate): ApplicationBuilder
    fun new()
    fun build(): RequestDelegate
}