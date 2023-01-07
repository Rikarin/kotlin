package net.rikarin.multitenancy

object CoroutineCurrentTenantAccessor : CurrentTenantAccessor {
    override var current: TenantInfo? = TODO()
}