package net.rikarin.componentModel

import kotlin.reflect.typeOf

class LongConverter : BaseNumberConverter() {
    override val targetType = typeOf<Long>()
    override fun fromString(value: String, radix: Int): Any = value.toLong(radix)
    override fun fromString(value: String): Any = value.toLong()
    override fun toString(value: Any): String = value.toString()
}