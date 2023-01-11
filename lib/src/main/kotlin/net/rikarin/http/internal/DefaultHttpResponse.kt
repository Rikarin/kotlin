package net.rikarin.http.internal

import net.rikarin.http.*
import java.io.OutputStream

internal class DefaultHttpResponse : HttpResponse() {
    private var _hasStarted = false

    override lateinit var httpContext: HttpContext
    override var statusCode = StatusCodes.status200OK
    override val headers = DefaultHeaderDictionary()
    override var body = OutputStream.nullOutputStream()
    override var contentLength: Long?
        get() = TODO("Not yet implemented")
        set(value) {}
    override var contentType: String?
        get() = TODO("Not yet implemented")
        set(value) {}
    override val hasStarted
        get() = _hasStarted

    override fun redirect(location: String, permanent: Boolean) {
        TODO("Not yet implemented")
    }

    override fun start() {
        TODO("Not yet implemented")
    }

    override fun complete() {
        TODO("Not yet implemented")
    }
}