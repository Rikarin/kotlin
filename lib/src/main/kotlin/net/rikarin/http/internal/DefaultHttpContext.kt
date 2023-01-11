package net.rikarin.http.internal

import net.rikarin.dependencyInjeciton.ServiceProvider
import net.rikarin.http.ConnectionInfo
import net.rikarin.http.HttpContext
import net.rikarin.http.HttpRequest
import net.rikarin.http.HttpResponse
import java.net.Socket

internal class DefaultHttpContext(
    private val socket: Socket,
    override val request: HttpRequest,
    override val response: HttpResponse,
    override val connection: ConnectionInfo
) : HttpContext() {
    override var items: Map<Any, Any?>
        get() = TODO("Not yet implemented")
        set(value) {}
    override var requestServices: ServiceProvider
        get() = TODO("Not yet implemented")
        set(value) {}
    override var traceIdentifier: String
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun abort() = socket.close()



    class FeatureInterfaces {
//        public IItemsFeature? Items;
//        public IServiceProvidersFeature? ServiceProviders;
//        public IHttpAuthenticationFeature? Authentication;
//        public IHttpRequestLifetimeFeature? Lifetime;
//        public ISessionFeature? Session;
//        public IHttpRequestIdentifierFeature? RequestIdentifier;
    }
}