package net.rikarin.componentModel

import kotlin.reflect.typeOf

class IntConverter : BaseNumberConverter() {
    override val targetType = typeOf<Int>()
    override fun fromString(value: String, radix: Int): Any = value.toInt(radix)
    override fun fromString(value: String): Any = value.toInt()
    override fun toString(value: Any): String = value.toString()
}