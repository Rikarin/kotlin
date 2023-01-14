package net.rikarin.http.internal

import net.rikarin.http.DefaultHeaderDictionary
import net.rikarin.http.HeaderDictionary
import net.rikarin.http.features.HttpResponseFeature

internal class DefaultHttpResponseFeature(
    override var statusCode: Int = 200,
    override var reasonPhrase: String? = null,
    override var headers: HeaderDictionary = DefaultHeaderDictionary(),
) : HttpResponseFeature {
    override val hasStarted: Boolean
        get() = false

    override fun onStarting(callback: () -> Unit) {

    }

    override fun onCompleted(callback: () -> Unit) {

    }
}