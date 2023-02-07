package net.rikarin.primitives

import net.rikarin.Disposable

interface ChangeToken {
    val hasChanged: Boolean
    val activeChangeCallbacks: Boolean

    fun registerChangeCallback(callback: () -> Unit): Disposable

    companion object {
        fun onChange(changeTokenProducer: () -> ChangeToken?, changeTokenConsumer: () -> Unit): Disposable =
            ChangeTokenRegistration(changeTokenProducer, changeTokenConsumer)
    }
}


private class ChangeTokenRegistration(
    private val changeTokenProducer: () -> ChangeToken?,
    private val changeTokenConsumer: () -> Unit
) : Disposable {

    init {
        registerChangeTokenCallback(changeTokenProducer())
    }

    override fun dispose() {
        // TODO: implement this
//        TODO("Not yet implemented")
    }

    private fun registerChangeTokenCallback(token: ChangeToken?) {
        if (token == null) {
            return
        }

        val registration = token.registerChangeCallback {
            val token = changeTokenProducer()
            try {
                changeTokenConsumer()
            } finally {
                registerChangeTokenCallback(token)
            }
        }

        setDisposable(registration)
    }

    private fun setDisposable(disposable: Disposable) {
        // TODO: implement this
//        TODO()
    }
}