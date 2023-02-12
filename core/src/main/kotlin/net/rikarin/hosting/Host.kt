package net.rikarin.hosting

import net.rikarin.dependencyInjeciton.ServiceProvider

interface Host {
    val services: ServiceProvider

    suspend fun start()
    suspend fun stop()
}