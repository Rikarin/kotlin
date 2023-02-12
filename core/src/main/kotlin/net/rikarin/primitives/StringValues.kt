package net.rikarin.primitives

// Immutable
class StringValues(values: Array<String>): ArrayList<String>() {
    companion object {
        val EMPTY = StringValues(emptyArray())
    }

    constructor(value: String) : this(arrayOf(value))

    init {
        addAll(values)
    }
}
