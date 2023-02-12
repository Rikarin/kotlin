package net.rikarin.componentModel

import kotlin.reflect.typeOf

class ShortConverter : BaseNumberConverter() {
    override val targetType = typeOf<Short>()
    override fun fromString(value: String, radix: Int): Any = value.toShort(radix)
    override fun fromString(value: String): Any = value.toShort()
    override fun toString(value: Any): String = value.toString()
}