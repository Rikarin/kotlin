package net.rikarin.componentModel

import kotlin.reflect.KType
import kotlin.reflect.typeOf

open class TypeConverter {
    open fun canConvertFrom(sourceType: KType) = false
    open fun canConvertTo(destinationType: KType) = destinationType == typeOf<String>()

    open fun convertFrom(value: Any): Any? {
        TODO()
    }

    open fun convertFromString(value: String): Any? = convertFrom(value)

    open fun convertTo(value: Any?, destinationType: KType): Any? {
        if (destinationType == typeOf<String>()) {
            if (value == null) {
                return ""
            }

            // TODO: Formattable & Formatter

            return value.toString()
        }

        TODO()
    }

    open fun convertToString(value: Any?) = convertTo(value, typeOf<String>()) as String
}



