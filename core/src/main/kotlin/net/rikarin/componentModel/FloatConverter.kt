package net.rikarin.componentModel

import kotlin.reflect.typeOf

class FloatConverter : BaseNumberConverter() {
    override val allowHex = false
    override val targetType = typeOf<Float>()

    override fun fromString(value: String, radix: Int): Any = value.toFloat()
    override fun fromString(value: String): Any = value.toFloat()
    override fun toString(value: Any): String = value.toString()
}