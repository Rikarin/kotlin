package net.rikarin.http

import net.rikarin.dependencyInjeciton.ServiceProvider
import net.rikarin.http.features.FeatureCollection

abstract class HttpContext {
    abstract val features: FeatureCollection
    abstract val request: HttpRequest
    abstract val response: HttpResponse
    abstract val connection: ConnectionInfo
//    public abstract WebSocketManager WebSockets { get; }
//    public abstract ClaimsPrincipal User { get; set; }
    abstract var items: MutableMap<Any, Any?> // TODO: Check this
    abstract var requestServices: ServiceProvider
//    public abstract CancellationToken RequestAborted { get; set; }
    abstract var traceIdentifier: String
//    public abstract ISession Session { get; set; }
    abstract fun abort()
}
