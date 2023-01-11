package net.rikarin.http.features

import org.junit.jupiter.api.Test

class FeatureReferencesTest {
    val collection = DefaultFeatureCollection()
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
