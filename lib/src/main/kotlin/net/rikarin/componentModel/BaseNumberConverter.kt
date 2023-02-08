package net.rikarin.componentModel

import kotlin.reflect.KType
import kotlin.reflect.typeOf

abstract class BaseNumberConverter : TypeConverter() {
    internal open val allowHex = true
    internal abstract val targetType: KType

    abstract fun fromString(value: String, radix: Int): Any
    abstract fun fromString(value: String): Any
    abstract fun toString(value: Any): String

    override fun canConvertFrom(sourceType: KType): Boolean =
        sourceType == typeOf<String>() || super.canConvertFrom(sourceType)

    override fun convertFrom(value: Any): Any? {
        if (value is String) {
            val text = value.trim()

            try {
                if (allowHex && text[0] == '#') {
                    return fromString(text.substring(0), 16)
                } else if (allowHex && (text.startsWith("0x", true)
                            || text.startsWith("&h", true))) {
                    return fromString(text.substring(2), 16)
                }

                return fromString(text)
            } catch (e: Exception) {
                throw IllegalArgumentException("TODO")
            }
        }

        return super.convertFrom(value)
    }

    override fun convertTo(value: Any?, destinationType: KType): Any? {
        // TODO

        return super.convertTo(value, destinationType)
    }
}