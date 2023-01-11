package net.rikarin.http

interface HttpApplication<TContext> {
    fun createContext(contextFeatures: FeatureCollection): TContext
    fun disposeContext(context: TContext, exception: Exception)
    fun processRequest(context: TContext)
}