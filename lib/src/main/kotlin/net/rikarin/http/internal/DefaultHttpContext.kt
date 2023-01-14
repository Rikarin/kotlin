package net.rikarin.http.internal

import net.rikarin.dependencyInjeciton.ServiceProvider
import net.rikarin.http.ConnectionInfo
import net.rikarin.http.HttpContext
import net.rikarin.http.features.*

internal class DefaultHttpContext(override val features: FeatureCollection) : HttpContext() {
    val _features: FeatureReferences<FeatureInterfaces>

    constructor() : this(DefaultFeatureCollection()) {
        features.set<HttpRequestFeature>(DefaultHttpRequestFeature())
        features.set<HttpResponseFeature>(DefaultHttpResponseFeature())
    }

    init {
        _features = FeatureReferences(features, ::FeatureInterfaces)
    }

    override val request = DefaultHttpRequest(this)
    override val response = DefaultHttpResponse(this)
    override val connection: ConnectionInfo by lazy { DefaultConnectionInfo(features) }
    override var items: MutableMap<Any, Any?>
        get() = itemsFeature.items
        set(value) { itemsFeature.items = value }
    override var requestServices: ServiceProvider
        get() = TODO("Not yet implemented")
        set(value) {}
    override var traceIdentifier: String
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun abort() {
        TODO()
    }

//    override fun abort() = socket.close()

    private var itemsFeature by _features.byProperty("items") { DefaultItemsFeature() }


    class FeatureInterfaces {
        var items: ItemsFeature? = null
//        public IServiceProvidersFeature? ServiceProviders;
//        public IHttpAuthenticationFeature? Authentication;
//        public IHttpRequestLifetimeFeature? Lifetime;
//        public ISessionFeature? Session;
//        public IHttpRequestIdentifierFeature? RequestIdentifier;
    }
}