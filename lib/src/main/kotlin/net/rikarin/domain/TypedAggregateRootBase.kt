package net.rikarin.domain

import net.rikarin.auditing.DisableAuditing
import net.rikarin.core.EventOrderGenerator
import net.rikarin.core.IdProvider
import java.util.*

abstract class TypedAggregateRootBase<T: Any> protected constructor() : TypedEntityBase<T>(), TypedAggregateRoot<T> {
    val _events = mutableListOf<DomainEventRecord>()
    val _integrationEvents = mutableListOf<DomainEventRecord>()

    @DisableAuditing
    override var concurrencyStamp: String? = null

    override val events: Collection<DomainEventRecord>
        get() = Collections.unmodifiableList(_events)

    override val integrationEvents: Collection<DomainEventRecord>
        get() = Collections.unmodifiableList(_integrationEvents)

    init {
        concurrencyStamp = IdProvider.Instance?.newId().toString()
    }

    protected constructor(id: T) : this() {
        this.id = id
    }

    override fun clearEvents() {
        _events.clear()
    }

    override fun clearIntegrationEvents() {
        _integrationEvents.clear()
    }

    fun addEvent(data: Any) {
        _events.add(DomainEventRecord(data, EventOrderGenerator.getNext()))
    }

    fun addIntegrationEvent(data: Any) {
        _integrationEvents.add(DomainEventRecord(data, EventOrderGenerator.getNext()))
    }
}