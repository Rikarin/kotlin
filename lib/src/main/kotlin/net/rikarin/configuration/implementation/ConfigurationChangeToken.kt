package net.rikarin.configuration.implementation

import net.rikarin.primitives.ChangeToken

class ConfigurationChangeToken : ChangeToken {
    override val hasChanged: Boolean
        get() = TODO("Not yet implemented")
    override val activeChangeCallbacks: Boolean
        get() = TODO("Not yet implemented")

    override fun registerChangeCallback(callback: () -> Unit) {
        TODO("Not yet implemented")
    }

    fun onReload() {
        TODO()
    }
}
