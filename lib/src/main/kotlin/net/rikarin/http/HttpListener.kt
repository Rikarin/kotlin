package net.rikarin.http

import net.rikarin.http.internal.DefaultConnectionInfo
import net.rikarin.http.internal.DefaultHttpContext
import net.rikarin.http.internal.DefaultHttpRequest
import net.rikarin.http.internal.DefaultHttpResponse
import net.rikarin.primitives.StringValues
import java.io.BufferedReader
import java.io.DataInputStream
import java.io.InputStream
import java.io.InputStreamReader
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
        val connection = DefaultConnectionInfo(
            client.inetAddress.asIPAddress(),
            client.port,
            client.localAddress.asIPAddress(),
            client.localPort
        )

        val request = DefaultHttpRequest()
        val response = DefaultHttpResponse()
        val context = DefaultHttpContext(client, request, response, connection)

        request.body = client.inputStream
        request.httpContext = context
        response.httpContext = context

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

        println("available ${request.body.available()}")

        val reader = BufferedReader(InputStreamReader(request.body))
        val messageline = reader.readLine()

        println("message $messageline")

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