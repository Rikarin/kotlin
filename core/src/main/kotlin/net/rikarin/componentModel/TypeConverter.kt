package net.rikarin.componentModel

import net.rikarin.*
import kotlin.reflect.KType
import kotlin.reflect.typeOf

open class TypeConverter {
    open fun canConvertFrom(sourceType: KType) = false
    open fun canConvertTo(destinationType: KType) = destinationType == typeOf<String>()

    open fun convertFrom(value: Any): Any? {
        throw convertFromException(value)
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

        throw convertToException(value, destinationType)
    }

    open fun convertToString(value: Any?) = convertTo(value, typeOf<String>()) as String

    protected fun convertFromException(value: Any?): Exception {
        val name = if (value != null) value::class.qualifiedName else "(null)"
        return NotSupportedException(CONVERT_FROM.format(this::class.qualifiedName, name))
    }

    protected fun convertToException(value: Any?, destinationType: KType): Exception {
        val name = if (value != null) value::class.qualifiedName else "(null)"
        return NotSupportedException(CONVERT_TO.format(this::class.qualifiedName, name, destinationType.asClass().qualifiedName))
    }
}



