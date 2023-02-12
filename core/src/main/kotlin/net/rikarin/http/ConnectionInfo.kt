package net.rikarin.http

abstract class ConnectionInfo {
    abstract var remoteIPAddress: IPAddress?
    abstract var remotePort: Int?
    abstract var localIPAddress: IPAddress?
    abstract var localPort: Int?
    // TODO: certificate stuff

    open fun requestClose() { }
}