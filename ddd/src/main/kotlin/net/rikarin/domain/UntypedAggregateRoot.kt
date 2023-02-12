package net.rikarin.domain

interface UntypedAggregateRoot : UntypedEntity, HasConcurrencyStamp {
    val events: Collection<DomainEventRecord>
    val integrationEvents: Collection<DomainEventRecord>

    fun clearEvents()
    fun clearIntegrationEvents()
}
