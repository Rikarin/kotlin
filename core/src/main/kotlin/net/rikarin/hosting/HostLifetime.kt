package net.rikarin.hosting


// Not sure if methods shouldn't be suspend
interface HostLifetime {
    fun waitToStart()
    fun stop()
}