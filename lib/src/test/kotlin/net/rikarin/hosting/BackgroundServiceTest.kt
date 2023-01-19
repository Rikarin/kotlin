package net.rikarin.hosting

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class BackgroundServiceTest {
    @Test
    fun createAndTestCustomService() {
        val service = CustomService()

        runBlocking {
            service.start()
            println("service started")

            delay(5000)

            println("stop requested")
            service.stop()
        }
    }
}


class CustomService : BackgroundService() {
    override suspend fun execute() {
        for (i in 0 .. 100) {
            println("foo")
            delay(1000)
        }
    }
}