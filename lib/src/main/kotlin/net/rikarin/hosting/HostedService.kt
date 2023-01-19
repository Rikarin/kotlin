package net.rikarin.hosting

interface HostedService {
    suspend fun start()
    suspend fun stop()
}
