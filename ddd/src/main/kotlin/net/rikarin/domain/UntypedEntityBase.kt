package net.rikarin.domain

abstract class UntypedEntityBase protected constructor() : UntypedEntity {
    init {
        EntityHelper.setTenantId(this)
    }

    fun entityEquals(other: UntypedEntity) = EntityHelper.entityEquals(this, other)

    override fun toString() = "[ENTITY: ${javaClass.name}] Keys = ${primaryKeys.joinToString(", ")}"
}