package net.rikarin.core

import java.util.concurrent.atomic.AtomicReference

fun <T> AtomicReference<T>.exchange(newValue: T): T {
    var oldValue: T

    do {
        oldValue = get()
    } while (!compareAndSet(oldValue, newValue))

    return oldValue
}
