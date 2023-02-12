package net.rikarin.componentModel

import kotlin.reflect.typeOf

class UShortConverter : BaseNumberConverter() {
    override val targetType = typeOf<UShort>()
    override fun fromString(value: String, radix: Int): Any = value.toUShort(radix)
    override fun fromString(value: String): Any = value.toUShort()
    override fun toString(value: Any): String = value.toString()
}