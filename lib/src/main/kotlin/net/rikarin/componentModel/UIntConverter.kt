package net.rikarin.componentModel

import kotlin.reflect.typeOf

class UIntConverter : BaseNumberConverter() {
    override val targetType = typeOf<UInt>()
    override fun fromString(value: String, radix: Int): Any = value.toUInt(radix)
    override fun fromString(value: String): Any = value.toUInt()
    override fun toString(value: Any): String = value.toString()
}