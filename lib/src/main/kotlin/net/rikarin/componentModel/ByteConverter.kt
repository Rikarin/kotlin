package net.rikarin.componentModel

import kotlin.reflect.typeOf

class ByteConverter : BaseNumberConverter() {
    override val targetType = typeOf<Byte>()
    override fun fromString(value: String, radix: Int): Any = value.toByte(radix)
    override fun fromString(value: String): Any = value.toByte()
    override fun toString(value: Any): String = value.toString()
}