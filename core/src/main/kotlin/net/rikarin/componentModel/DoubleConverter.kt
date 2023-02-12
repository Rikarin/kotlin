package net.rikarin.componentModel

import kotlin.reflect.typeOf

class DoubleConverter : BaseNumberConverter() {
    override val allowHex = false
    override val targetType = typeOf<Double>()

    override fun fromString(value: String, radix: Int): Any = value.toDouble()
    override fun fromString(value: String): Any = value.toDouble()
    override fun toString(value: Any): String = value.toString()
}