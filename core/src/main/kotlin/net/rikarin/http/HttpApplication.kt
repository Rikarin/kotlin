package net.rikarin.http

import net.rikarin.http.features.FeatureCollection

interface HttpApplication<TContext> {
    fun createContext(contextFeatures: FeatureCollection): TContext
    fun disposeContext(context: TContext, exception: Exception)
    fun processRequest(context: TContext)
}