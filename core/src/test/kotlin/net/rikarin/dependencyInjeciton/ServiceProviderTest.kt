package net.rikarin.dependencyInjeciton

import net.rikarin.use
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ServiceProviderTest {
    @Test
    fun testServiceProvider() {
        val serviceCollection = DefaultServiceCollection()
        serviceCollection.addSingleton<FooBar>(FooBar("foo", "bar"))
        serviceCollection.addScoped<TestInterface, TestClass>()

        val provider = serviceCollection.buildServiceProvider(ServiceProviderOptions(true, true))

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

    @Test
    fun openGenericTest() {
        val serviceCollection = DefaultServiceCollection()
        serviceCollection.addTransient<BasicClass, BasicClass>()
        serviceCollection.addSingleton<BasicClass2>(BasicClass2("foobar"))

        serviceCollection.addTransient<GenericClass<*>, GenericClass<*>>()
        serviceCollection.addTransient<SimpleClass<*>, SimpleClass<*>>()
        serviceCollection.addTransient<SomeClass<*, *>, SomeClass<*, *>>()

        val provider = serviceCollection.buildServiceProvider()

        val genericBasic = provider.getRequiredService<SomeClass<BasicClass, BasicClass2>>()
        assertEquals("foobar", genericBasic.value.value)
        genericBasic.value.value = "barbar"

        val genericBasic2 = provider.getRequiredService<SomeClass<BasicClass, BasicClass2>>()
        assertEquals("barbar", genericBasic2.value.value)
    }
}

class BasicClass
class BasicClass2(var value: String)

class SimpleClass<T>
class SomeClass<T, U>(val value: U, val value2: T)

class GenericClass<T>(val value: T)


data class FooBar(val foo: String, val bar: String)

private interface TestInterface {
    var name: String
    fun calculateFoo(): String
    fun calculateBar(): String?
}

class TestClass(private val fooBar: FooBar) : TestInterface {
    constructor() : this(FooBar("asdf", "asdf")) { }

    @Inject
    private var serviceProvider: ServiceProvider? = null

    override var name: String = ""
    override fun calculateFoo() = fooBar.foo
    override fun calculateBar() = serviceProvider?.getRequiredService<FooBar>()?.bar
}
