package net.rikarin.dependencyInjeciton

import net.rikarin.use
import org.junit.jupiter.api.Test
import kotlin.reflect.full.primaryConstructor
import kotlin.test.assertEquals

class ServiceProviderTest {
    @Test
    fun testServiceProvider() {
        val serviceCollection = DefaultServiceCollection()
        serviceCollection.addSingleton<FooBar>(FooBar("foo", "bar"))
        serviceCollection.addScoped<TestInterface, TestClass>()

        val provider = serviceCollection.buildServiceProvider()

        provider.createScope().use {
            val instance = it.serviceProvider.getRequiredService<TestInterface>()
            val instance2 = it.serviceProvider.getRequiredService<TestInterface>()

            assertEquals("", instance.name)
            assertEquals("", instance2.name)
            assertEquals("foo", instance.calculateFoo())

            instance.name = "test name"
            assertEquals("test name", instance.name)
            assertEquals("test name", instance2.name)
        }

        provider.createScope().use {
            val instance = it.serviceProvider.getRequiredService<TestInterface>()

            assertEquals("", instance.name)
            assertEquals("foo", instance.calculateFoo())

            instance.name = "test name"
            assertEquals("test name", instance.name)
            assertEquals("bar", instance.calculateBar())
        }
    }

    @Test
    fun genericTest() {
        val serviceCollection = DefaultServiceCollection()
        serviceCollection.addSingleton<GenericClass<String>>(GenericClass("foo"))
        serviceCollection.addSingleton<GenericClass<Int>>(GenericClass(42))

        val provider = serviceCollection.buildServiceProvider()

        val str = provider.getRequiredService<GenericClass<String>>()
        val num = provider.getRequiredService<GenericClass<Int>>()

        assertEquals("foo", str.value)
        assertEquals(42, num.value)
    }
}

class GenericClass<T>(val value: T)


data class FooBar(val foo: String, val bar: String)

private interface TestInterface {
    var name: String
    fun calculateFoo(): String
    fun calculateBar(): String?
}

class TestClass(private val fooBar: FooBar) : TestInterface {
    @Inject
    var serviceProvider: ServiceProvider? = null

    override var name: String = ""
    override fun calculateFoo() = fooBar.foo
    override fun calculateBar() = serviceProvider?.getRequiredService<FooBar>()?.bar
}
