package net.rikarin.http.internal

import net.rikarin.http.DefaultHeaderDictionary
import net.rikarin.http.HeaderDictionary
import net.rikarin.http.features.HttpRequestFeature
import java.io.OutputStream

internal class DefaultHttpRequestFeature(
    override var protocol: String = "",
    override var scheme: String = "",
    override var method: String = "",
    override var pathBase: String = "",
    override var path: String = "",
    override var queryString: String = "",
    override var rawTarget: String = "",
    override var headers: HeaderDictionary = DefaultHeaderDictionary(),
    override var body: OutputStream = OutputStream.nullOutputStream()
) : HttpRequestFeature
