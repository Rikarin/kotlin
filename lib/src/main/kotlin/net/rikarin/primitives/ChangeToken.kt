package net.rikarin.primitives

interface ChangeToken {
    val hasChanged: Boolean
    val activeChangeCallbacks: Boolean

    fun registerChangeCallback(callback: () -> Unit)
}