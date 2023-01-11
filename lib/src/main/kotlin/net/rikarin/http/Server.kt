package net.rikarin.http

interface Server {
    val features: FeatureCollection

    fun <TContext> start(application: TContext)
    fun stop()
}