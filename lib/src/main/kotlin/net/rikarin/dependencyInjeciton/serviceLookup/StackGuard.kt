package net.rikarin.dependencyInjeciton.serviceLookup

const val MAX_EXECUTION_STACK_COUNT = 1024

internal class StackGuard {
    private var _executionStackCount = 0

    fun tryEnterOnCurrentStack() {
        if (_executionStackCount >= MAX_EXECUTION_STACK_COUNT) {
            throw InsufficientExecutionStackException()
        }
    }

//    fun run()
}

class InsufficientExecutionStackException : Exception()