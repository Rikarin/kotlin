package net.rikarin.http

import net.rikarin.http.features.FeatureCollection

interface HttpContextFactory {
    fun create(featureCollection: FeatureCollection): HttpContext
    fun dispose(httpContext: HttpContext)
}
