package net.rikarin.configuration

typealias BinderOptionsAction = (BinderOptions) -> Unit

data class BinderOptions(
    var bindNonPublicProperties: Boolean = false,
    var errorOnUnknownConfiguration: Boolean = false
)