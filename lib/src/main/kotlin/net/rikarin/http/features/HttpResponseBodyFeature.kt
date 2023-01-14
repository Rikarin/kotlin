package net.rikarin.http.features

import java.io.OutputStream

interface HttpResponseBodyFeature {
    val stream: OutputStream
    // pipewriter

    fun disableBuffering()
    fun start()
    fun sendFile(path: String, offset: Long, count: Long?)
    fun complete()
}