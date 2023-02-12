package net.rikarin.http

import net.rikarin.primitives.StringValues
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class HeaderDictionary : MutableMap<String, StringValues> {
    abstract var contentLength: Int

    var accept by getProperty(HeaderNames.ACCEPT)
    var acceptCharset by getProperty(HeaderNames.ACCEPT_CHARSET)
    var acceptEncoding by getProperty(HeaderNames.ACCEPT_ENCODING)
    var acceptLanguage by getProperty(HeaderNames.ACCEPT_LANGUAGE)
    var acceptRanges by getProperty(HeaderNames.ACCEPT_RANGES)
    var accessControlAllowCredentials by getProperty(HeaderNames.ACCESS_CONTROL_ALLOW_CREDENTIALS)
    var accessControlAllowHeaders by getProperty(HeaderNames.ACCESS_CONTROL_ALLOW_HEADERS)
    var accessControlAllowMethods by getProperty(HeaderNames.ACCESS_CONTROL_ALLOW_METHODS)
    var accessControlAllowOrigin by getProperty(HeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN)
    var accessControlExposeHeaders by getProperty(HeaderNames.ACCESS_CONTROL_EXPOSE_HEADERS)
    var accessControlMaxAge by getProperty(HeaderNames.ACCESS_CONTROL_MAX_AGE)
    var accessControlRequestHeaders by getProperty(HeaderNames.ACCESS_CONTROL_REQUEST_HEADERS)
    var accessControlRequestMethod by getProperty(HeaderNames.ACCESS_CONTROL_REQUEST_METHOD)
    var age by getProperty(HeaderNames.AGE)
    var allow by getProperty(HeaderNames.ALLOW)
    var altSvc by getProperty(HeaderNames.ALT_SVC)
    var authorization by getProperty(HeaderNames.AUTHORIZATION)
    var baggage by getProperty(HeaderNames.BAGGAGE)
    var cacheControl by getProperty(HeaderNames.CACHE_CONTROL)
    var connection by getProperty(HeaderNames.CONNECTION)
    var contentDisposition by getProperty(HeaderNames.CONTENT_DISPOSITION)
    var contentEncoding by getProperty(HeaderNames.CONTENT_ENCODING)
    var contentLanguage by getProperty(HeaderNames.CONTENT_LANGuAGE)
    var contentLocation by getProperty(HeaderNames.CONTENT_LOCATION)
    var contentMD5 by getProperty(HeaderNames.CONTENT_MD5)
    var contentRange by getProperty(HeaderNames.CONTENT_RANGE)
    var contentSecurityPolicy by getProperty(HeaderNames.CONTENT_SECURITY_POLICY)
    var contentSecurityPolicyReportOnly by getProperty(HeaderNames.CONTENT_SECURITY_POLICY_REPORT_ONLY)
    var contentType by getProperty(HeaderNames.CONTENT_TYPE)
    var correlationContext by getProperty(HeaderNames.CORRELATION_CONTEXT)
    var cookie by getProperty(HeaderNames.COOKIE)
    var date by getProperty(HeaderNames.DATE)
    var dnt by getProperty(HeaderNames.DNT)
    var eTag by getProperty(HeaderNames.E_TAG)
    var expires by getProperty(HeaderNames.EXPIRES)
    var expect by getProperty(HeaderNames.EXPECT)
    var from by getProperty(HeaderNames.FROM)
    var grpcAcceptEncoding by getProperty(HeaderNames.GRPC_ACCEPT_ENCODING)
    var grpcEncoding by getProperty(HeaderNames.GRPC_ENCODING)
    var grpcMessage by getProperty(HeaderNames.GRPC_MESSAGE)
    var grpcStatus by getProperty(HeaderNames.GRPC_STATUS)
    var grpcTimeout by getProperty(HeaderNames.GRPC_TIMEOUT)
    var host by getProperty(HeaderNames.HOST)
    var keepAlive by getProperty(HeaderNames.KEEP_ALIVE)
    var ifMatch by getProperty(HeaderNames.IF_MATCH)
    var ifModifiedSince by getProperty(HeaderNames.IF_MODIFIED_SINCE)
    var ifNoneMatch by getProperty(HeaderNames.IF_NONE_MATCH)
    var ifRange by getProperty(HeaderNames.IF_RANGE)
    var ifUnmodifiedSince by getProperty(HeaderNames.IF_UNMODIFIED_SINCE)
    var lastModified by getProperty(HeaderNames.LAST_MODIFIED)
    var link by getProperty(HeaderNames.LINK)
    var location by getProperty(HeaderNames.LOCATION)
    var maxforwards by getProperty(HeaderNames.MAX_FORWARDS)
    var origin by getProperty(HeaderNames.ORIGIN)
    var pragma by getProperty(HeaderNames.PRAGMA)
    var proxyAuthenticate by getProperty(HeaderNames.PROXY_AUTHENTICATE)
    var proxyAuthorization by getProperty(HeaderNames.PROXY_AUTHORIZATION)
    var proxyConnection by getProperty(HeaderNames.PROXY_CONNECTION)
    var range by getProperty(HeaderNames.RANGE)
    var referer by getProperty(HeaderNames.REFERER)
    var retryAfter by getProperty(HeaderNames.RETRY_AFTER)
    var requestId by getProperty(HeaderNames.REQUEST_ID)
    var secWebSocketAccept by getProperty(HeaderNames.SEC_WEB_SOCKET_ACCEPT)
    var secWebSocketKey by getProperty(HeaderNames.SEC_WEB_SOCKET_KEY)
    var secWebSocketProtocol by getProperty(HeaderNames.SEC_WEB_SOCKET_PROTOCOL)
    var secWebSocketVersion by getProperty(HeaderNames.SEC_WEB_SOCKET_VERSION)
    var secWebSocketExtensions by getProperty(HeaderNames.SEC_WEB_SOCKET_EXTENSIONS)
    var server by getProperty(HeaderNames.SERVER)
    var setCookie by getProperty(HeaderNames.SET_COOKIE)
    var strictTransportSecurity by getProperty(HeaderNames.STRICT_TRANSPORT_SECURITY)
    var te by getProperty(HeaderNames.TE)
    var trailer by getProperty(HeaderNames.TRAILER)
    var transferEncoding by getProperty(HeaderNames.TRANSFER_ENCODING)
    var translate by getProperty(HeaderNames.TRANSLATE)
    var traceParent by getProperty(HeaderNames.TRACE_PARENT)
    var traceState by getProperty(HeaderNames.TRACE_STATE)
    var upgrade by getProperty(HeaderNames.UPGRADE)
    var upgradeInsecureRequests by getProperty(HeaderNames.UPGRADE_INSECURE_REQUESTS)
    var userAgent by getProperty(HeaderNames.USER_AGENT)
    var vary by getProperty(HeaderNames.VARY)
    var via by getProperty(HeaderNames.VIA)
    var warning by getProperty(HeaderNames.WARNING)
    var webSocketSubProtocols by getProperty(HeaderNames.WEB_SOCKET_SUB_PROTOCOLS)
    var wwwAuthenticate by getProperty(HeaderNames.WWW_AUTHENTICATE)
    var xContentTypeOptions by getProperty(HeaderNames.X_CONTENT_TYPE_OPTIONS)
    var xFrameOptions by getProperty(HeaderNames.X_FRAME_OPTIONS)
    var xPoweredBy by getProperty(HeaderNames.X_POWERED_BY)
    var xRequestedWith by getProperty(HeaderNames.X_REQUESTED_WITH)
    var xUACompatible by getProperty(HeaderNames.X_UA_COMPATIBLE)
    var xXSSProtection by getProperty(HeaderNames.X_XSS_PROTECTION)

    protected fun getProperty(key: String): ReadWriteProperty<HeaderDictionary, StringValues> {
        return object : ReadWriteProperty<HeaderDictionary, StringValues> {
            override fun getValue(thisRef: HeaderDictionary, property: KProperty<*>): StringValues {
                return thisRef[key] ?: StringValues.EMPTY
            }

            override fun setValue(thisRef: HeaderDictionary, property: KProperty<*>, value: StringValues) {
                thisRef[key] = value
            }
        }
    }
}


