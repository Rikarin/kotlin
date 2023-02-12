package net.rikarin.http.features

import net.rikarin.http.HeaderDictionary

interface HttpResponseFeature {
    var statusCode: Int
    var reasonPhrase: String?
    var headers: HeaderDictionary
    val hasStarted: Boolean

    fun onStarting(callback: () -> Unit)
    fun onCompleted(callback: () -> Unit)
}