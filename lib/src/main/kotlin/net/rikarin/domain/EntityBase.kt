package net.rikarin.domain

abstract class EntityBase protected constructor() : Entity {
    override fun toString() =
        "[ENTITY: ${javaClass.name}] Keys = ${primaryKeys.joinToString(", ")}"
    /*
        protected Entity() {
            EntityHelper.TrySetTenantId(this);
        }

        public bool EntityEquals(IEntity other) => EntityHelper.EntityEquals(this, other);
     */
}