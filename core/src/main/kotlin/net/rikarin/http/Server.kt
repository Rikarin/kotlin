package net.rikarin.http

import net.rikarin.http.features.FeatureCollection

interface Server {
    val features: FeatureCollection

    fun <TContext> start(application: TContext)
    fun stop()
}