package net.rikarin.domain

interface AggregateRoot : Entity, HasConcurrencyStamp {
    val events: Collection<DomainEventRecord>
    val integrationEvents: Collection<DomainEventRecord>

    fun clearEvents()
    fun clearIntegrationEvents()
}
