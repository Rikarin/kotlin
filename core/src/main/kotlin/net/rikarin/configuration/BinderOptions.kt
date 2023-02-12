package net.rikarin.configuration

typealias BinderOptionsAction = (BinderOptions) -> Unit

// TODO: these are not handled
data class BinderOptions(
    var bindNonPublicProperties: Boolean = false,
    var errorOnUnknownConfiguration: Boolean = false
)