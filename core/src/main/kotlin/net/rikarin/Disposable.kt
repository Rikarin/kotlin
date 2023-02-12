package net.rikarin

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

fun interface Disposable {
    fun dispose()
}

@OptIn(ExperimentalContracts::class)
inline fun <T : Disposable?, R> T.use(block: (T) -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    var exception: Throwable? = null
    try {
        return block(this)
    } catch (e: Throwable) {
        exception = e
        throw e
    } finally {
        when {
            this == null -> {}
            exception == null -> dispose()
            else ->
                try {
                    dispose()
                } catch (_: Throwable) {

                }
        }
    }
}