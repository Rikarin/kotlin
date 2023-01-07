package net.rikarin.multitenancy

import net.rikarin.core.ID

interface CurrentTenant {
    val id: ID?
    val name: String?
    val isAvailable: Boolean

    fun change(id: ID?, name: String?): AutoCloseable
}
