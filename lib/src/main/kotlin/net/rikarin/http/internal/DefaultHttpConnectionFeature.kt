package net.rikarin.http.internal

import net.rikarin.http.IPAddress
import net.rikarin.http.features.HttpConnectionFeature

internal class DefaultHttpConnectionFeature(
    override var connectionId: String,
    override var remoteIpAddress: IPAddress?,
    override var localIpAddress: IPAddress?,
    override var remotePort: Int?,
    override var localPort: Int?
) : HttpConnectionFeature