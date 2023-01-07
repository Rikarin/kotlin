package net.rikarin.domain

abstract class TypedEntityBase<T : Any> protected constructor() : EntityBase(), Entity {
    lateinit var id: T
        protected set

    override val primaryKeys: Collection<Any>
        get() = listOf(id)

    protected constructor(id: T) : this() {
        this.id = id
    }

    override fun toString() = "[ENTITY: ${javaClass.name}] Id = ${id}"
}