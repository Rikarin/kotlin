package net.rikarin.http

import net.rikarin.http.features.*
import net.rikarin.http.internal.DefaultHttpConnectionFeature
import net.rikarin.http.internal.DefaultHttpContext
import net.rikarin.http.internal.DefaultItemsFeature
import net.rikarin.primitives.StringValues
import java.io.DataInputStream
import java.io.InputStream
import java.net.ServerSocket
import java.net.Socket

// Owin Implementation Example https://jakeydocs.readthedocs.io/en/latest/fundamentals/owin.html
// Parse HTTP request https://stackoverflow.com/questions/13255622/parsing-raw-http-request
// Limit max request size http://www.java2s.com/Code/Java/File-Input-Output/SizeLimitInputStream.htm

// Listeners
// API .NET https://learn.microsoft.com/en-us/dotnet/api/system.net.httplistener?view=net-7.0
// OWin Code https://github.com/aspnet/AspNetKatana/blob/dbe159e43e2eee44f315f26268943e8ab5a4f60d/src/Microsoft.Owin.Host.HttpListener/OwinHttpListener.cs
// Owin Engine https://github.com/aspnet/AspNetKatana/blob/dbe159e43e2eee44f315f26268943e8ab5a4f60d/src/Microsoft.Owin.Hosting/Engine/HostingEngine.cs


class HttpListener {
    // visit: https://stackoverflow.com/questions/5079172/java-server-multiple-ports
    private var _socket: ServerSocket? = null // TODO: implement multiple endpoints

    fun start() {
        if (_socket != null) {
            throw Exception("server already started")
        }

        _socket = ServerSocket(8080)
    }

    fun getContext(): HttpContext {
        if (_socket == null) {
            throw Exception("server has not started. call .start()")
        }

        var context: HttpContext?
        // ignore preflight requests
        do {
            context = getContextForSocket(_socket!!.accept())
        } while (context == null)

        return context
    }

    private fun getContextForSocket(client: Socket): HttpContext? {
        val collection = DefaultFeatureCollection()

        collection.set<ItemsFeature>(DefaultItemsFeature())
        collection.set<HttpConnectionFeature>(DefaultHttpConnectionFeature(
            "asdf",
            client.inetAddress.asIPAddress(),
            client.localAddress.asIPAddress(),
            client.port,
            client.localPort
        ))



        val context = DefaultHttpContext(collection)
//        request.body = client.inputStream
//        val scanner = Scanner(client.inputStream)
//        while (scanner.hasNextLine()) {
//            println(scanner.nextLine())
//        }

        val headers = parseHeaders(client.inputStream)
        if (headers == null) {
            // preflight
            client.close()
            return null
        }

        // TODO: parse headers and provide body stream

        println("context stuff ${context.connection.localPort} ${client.localPort}")

//        println("available ${request.body.available()}")

//        val reader = BufferedReader(InputStreamReader(request.body))
//        val messageline = reader.readLine()
//
//        println("message $messageline")

        return context
    }


    private fun parseHeaders(stream: InputStream): HeaderDictionary? {
        val headerDictionary = DefaultHeaderDictionary()
        val reader = DataInputStream(stream)
        val requestLine = reader.readLine() ?: return null

        println("request line $requestLine")

        var header = reader.readLine()
        while (header.isNotEmpty()) {
            addHeaderParameter(headerDictionary, header)
            header = reader.readLine()
        }

        return headerDictionary
    }

    private fun addHeaderParameter(dictionary: HeaderDictionary, line: String) {
        val idx = line.indexOf(':')
        if (idx == -1) {
            throw Exception("invalid header parameter")
        }

        val key = line.substring(0, idx).trim()
        val value = line.substring(idx + 1, line.length).trim()

        dictionary[key] = StringValues(value)
    }
}