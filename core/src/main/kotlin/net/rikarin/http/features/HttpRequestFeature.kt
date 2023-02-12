package net.rikarin.http.features

import net.rikarin.http.HeaderDictionary
import java.io.OutputStream

interface HttpRequestFeature {
    var protocol: String // e.g. HTTP/1.1
    var scheme: String // http/https
    var method: String // GET, HEAD, POST
    var pathBase: String
    var path: String
    var queryString: String
    var rawTarget: String // ??
    var headers: HeaderDictionary
    var body: OutputStream
}