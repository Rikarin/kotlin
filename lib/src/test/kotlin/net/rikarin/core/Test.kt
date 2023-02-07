package net.rikarin.core

import net.rikarin.http.HttpListener
import java.io.BufferedReader
import java.io.StringReader
import java.net.ServerSocket
import java.util.Scanner
import kotlin.test.Test


// TODO: finish file and Yaml config implementations
class Test {
    @Test
    fun testHttp() {
//        val listener = HttpListener()
//        val server = ServerSocket(8080)

//        listener.start()
//        println("Server running on port ${server.localPort}")

//        while (true) {
//            val context = listener.getContext()
//            println("context $context")

//            val client = server.accept()
//            println("Client connected : ${client.inetAddress.hostAddress}")
//            val scanner = Scanner(client.inputStream)
//            while (scanner.hasNextLine()) {
//                println(scanner.nextLine())
////                break
//            }

//             val reader = BufferedReader(StringReader(client.getInputStream()));
//
//            setRequestLine(reader.readLine()); // Request-Line ; Section 5.1
//
//            String header = reader.readLine();
//            while (header.length() > 0) {
//                appendHeaderParameter(header);
//                header = reader.readLine();
//            }
//
//            String bodyLine = reader.readLine();
//            while (bodyLine != null) {
//                appendMessageBody(bodyLine);
//                bodyLine = reader.readLine();
//            }
//        }
    }
}