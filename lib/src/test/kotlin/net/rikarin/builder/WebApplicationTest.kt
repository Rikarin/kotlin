package net.rikarin.builder

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class WebApplicationTest {
    @Test
    fun createExampleWebApplication() {
        val builder = WebApplication.createBuilder()
//        builder.services.add()

        val app = builder.build()
        // app.use...

        runBlocking {
            app.run()
        }
    }
}