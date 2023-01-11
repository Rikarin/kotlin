package net.rikarin.http.internal

import net.rikarin.http.HeaderDictionary
import net.rikarin.http.HttpContext
import net.rikarin.http.HttpRequest
import java.io.InputStream

internal class DefaultHttpRequest : HttpRequest() {
    override lateinit var httpContext: HttpContext

    override var method: String
        get() = TODO("Not yet implemented")
        set(value) {}
    override var scheme: String
        get() = TODO("Not yet implemented")
        set(value) {}
    override var isHttps: String
        get() = TODO("Not yet implemented")
        set(value) {}
    override var protocol: String
        get() = TODO("Not yet implemented")
        set(value) {}
    override val headers: HeaderDictionary
        get() = TODO("Not yet implemented")
    override var contentLength: Long?
        get() = TODO("Not yet implemented")
        set(value) {}
    override var contentType: String?
        get() = TODO("Not yet implemented")
        set(value) {}

    override lateinit var body: InputStream
    override var hasFormContentType: Boolean
        get() = TODO("Not yet implemented")
        set(value) {}

}