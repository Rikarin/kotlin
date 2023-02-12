package net.rikarin.http

import java.net.InetAddress

class IPAddress(inet: InetAddress)

fun InetAddress.asIPAddress(): IPAddress {
    return IPAddress(this)
}