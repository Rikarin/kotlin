package net.rikarin.domain

import net.rikarin.core.DefaultIdProvider
import net.rikarin.core.ID
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class AggregateRootTest {
    @Test
    fun createAggregate() {
        val aggregate = TestAggregate(ID(42))

        // WTF
        assertEquals("null", aggregate.concurrencyStamp)
    }

    @Test
    fun createAggregateWithIdProvider() {
        // This will initialize global variable used to generate unique id
        DefaultIdProvider()
        val aggregate = TestAggregate()

        assertNotEquals("null", aggregate.concurrencyStamp)
    }
}

private class TestAggregate : TypedAggregateRootBase<ID> {
    constructor() : super()
    constructor(id: ID) : super(id)
}
