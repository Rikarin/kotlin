package net.rikarin.multitenancy

import net.rikarin.Disposable
import net.rikarin.core.ID
import net.rikarin.dependencyInjeciton.TransientDependency

class BasicCurrentTenant(private val accessor: CurrentTenantAccessor) : CurrentTenant, TransientDependency {
    override val id = accessor.current?.id
    override val name = accessor.current?.name
    override val isAvailable = name != null

    override fun change(id: ID?, name: String?): Disposable {
        val parent = accessor.current
        accessor.current = TenantInfo(id, name)

        return Disposable { accessor.current = parent }
    }
}