package net.rikarin.configuration

interface ChangeToken {
    // TODO: move this interface outside of configuration

    val hasChanged: Boolean
    val activeChangeCallbacks: Boolean

    fun registerChangeCallback(callback: () -> Unit)
}