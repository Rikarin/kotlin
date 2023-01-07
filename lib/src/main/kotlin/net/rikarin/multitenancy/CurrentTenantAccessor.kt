package net.rikarin.multitenancy

interface CurrentTenantAccessor {
    var current: TenantInfo?
}
