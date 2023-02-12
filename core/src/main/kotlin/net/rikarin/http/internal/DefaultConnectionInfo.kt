package net.rikarin.http.internal

import net.rikarin.http.ConnectionInfo
import net.rikarin.http.IPAddress
import net.rikarin.http.features.*

internal class DefaultConnectionInfo(collection: FeatureCollection) : ConnectionInfo() {
    private val _features: FeatureReferences<FeatureInterfaces>

    override var remoteIPAddress: IPAddress?
        get() = connectionFeature.remoteIpAddress
        set(value) { connectionFeature.remoteIpAddress = value }
    override var remotePort: Int?
        get() = connectionFeature.remotePort
        set(value) { connectionFeature.remotePort = value }
    override var localIPAddress: IPAddress?
        get() = connectionFeature.localIpAddress
        set(value) { connectionFeature.localIpAddress = value }
    override var localPort: Int?
        get() = connectionFeature.localPort
        set(value) { connectionFeature.localPort = value }

    init {
        _features = FeatureReferences(collection, ::FeatureInterfaces)
    }

    private var connectionFeature: HttpConnectionFeature by _features.byProperty(FeatureInterfaces::httpConnectionFeature.name) {
        DefaultHttpConnectionFeature("", null, null, null, null)
    }

    class FeatureInterfaces {
        var httpConnectionFeature: HttpConnectionFeature? = null
    }
}
