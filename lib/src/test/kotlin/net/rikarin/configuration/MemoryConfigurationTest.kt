package net.rikarin.configuration

import net.rikarin.configuration.implementation.DefaultConfigurationBuilder
import net.rikarin.configuration.implementation.addInMemoryCollection
import org.junit.jupiter.api.Test

class MemoryConfigurationTest {
    @Test
    fun createMemoryConfiguration() {
        val configurationBuilder = DefaultConfigurationBuilder()
        configurationBuilder.addInMemoryCollection(mapOf("foo" to "fooValue", "bar" to "barValue"))

        val configuration = configurationBuilder.build()

        val foo = configuration["foo"]
        println("value $foo")
    }
}
