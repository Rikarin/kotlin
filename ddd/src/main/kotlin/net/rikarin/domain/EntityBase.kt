package net.rikarin.domain

abstract class EntityBase<T : Any> protected constructor() : UntypedEntityBase(), Entity<T> {
    override lateinit var id: T
        protected set

    override val primaryKeys: Collection<Any>
        get() = listOf(id)

    protected constructor(id: T) : this() {
        this.id = id
    }

    override fun toString() = "[ENTITY: ${javaClass.name}] Id = ${id}"
}