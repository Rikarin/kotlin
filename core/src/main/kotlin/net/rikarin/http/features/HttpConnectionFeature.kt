package net.rikarin.http.features

import net.rikarin.http.IPAddress

interface HttpConnectionFeature {
    var connectionId: String
    var remoteIpAddress: IPAddress?
    var localIpAddress: IPAddress?
    var remotePort: Int?
    var localPort: Int?
}
