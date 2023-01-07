package net.rikarin.core

import java.util.concurrent.atomic.AtomicLong

object EventOrderGenerator {
    private var _last = AtomicLong(0)

    fun getNext() = _last.getAndAdd(1)
}