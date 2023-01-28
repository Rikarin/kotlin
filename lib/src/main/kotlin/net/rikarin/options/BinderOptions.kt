package net.rikarin.options

typealias BinderOptionsAction = (BinderOptions) -> Unit

data class BinderOptions(
    val bindNonPublicProperties: Boolean = false,
    val errorOnUnknownConfiguration: Boolean = false
)