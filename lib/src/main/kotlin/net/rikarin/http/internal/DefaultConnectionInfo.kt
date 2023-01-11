package net.rikarin.http.internal

import net.rikarin.http.ConnectionInfo
import net.rikarin.http.IPAddress

internal class DefaultConnectionInfo(
    override var remoteIPAddress: IPAddress?,
    override var remotePort: Int?,
    override var localIPAddress: IPAddress?,
    override var localPort: Int?
) : ConnectionInfo()