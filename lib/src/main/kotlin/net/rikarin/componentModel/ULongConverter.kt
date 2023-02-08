package net.rikarin.componentModel

import kotlin.reflect.typeOf

class ULongConverter : BaseNumberConverter() {
    override val targetType = typeOf<ULong>()
    override fun fromString(value: String, radix: Int): Any = value.toULong(radix)
    override fun fromString(value: String): Any = value.toULong()
    override fun toString(value: Any): String = value.toString()
}