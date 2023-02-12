package net.rikarin.componentModel

import kotlin.reflect.typeOf

class UByteConverter : BaseNumberConverter() {
    override val targetType = typeOf<UByte>()
    override fun fromString(value: String, radix: Int): Any = value.toUByte(radix)
    override fun fromString(value: String): Any = value.toUByte()
    override fun toString(value: Any): String = value.toString()
}