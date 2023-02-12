package net.rikarin.multitenancy

import net.rikarin.core.ID

interface MultiTenant {
    val tenantId: ID?
}