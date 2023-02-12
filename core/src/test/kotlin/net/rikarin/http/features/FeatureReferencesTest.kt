package net.rikarin.http.features

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class FeatureReferencesTest {
    private val collection = DefaultFeatureCollection()
    val references = FeatureReferences(collection, ::FeatureInterfaces)

    var fooRef: Foo by references.byProperty("foo") { Foo("oi") }

    @Test
   fun testFeatureReference() {
       println("fooRef ${fooRef.value}")

   }
}

class Foo(val value: String)
class Bar

class FeatureInterfaces {
    var foo: Foo? = null
    var bar: Bar? = null
}


// write tests for FeatureCollection
class FeatureCollectionTest {
    private val collection = DefaultFeatureCollection()

    @Test
    fun testFeatureCollection() {
        collection.set(Foo("oi"))
        collection.set(Bar())

        val foo = collection.get<Foo>()
        val bar = collection.get<Bar>()

        assertEquals(foo.value, "oi")
        assertNotEquals(bar, Bar())
    }
}
