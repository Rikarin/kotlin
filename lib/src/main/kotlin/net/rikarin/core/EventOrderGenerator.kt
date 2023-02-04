package net.rikarin.core

import java.util.concurrent.atomic.AtomicLong

// TODO: consider moving this to the domain package to not overlap with tracing's EventSource stuff
object EventOrderGenerator {
    private var _last = AtomicLong(0)

    fun getNext() = _last.getAndAdd(1)
}