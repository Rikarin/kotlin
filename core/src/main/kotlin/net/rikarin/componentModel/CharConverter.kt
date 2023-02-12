package net.rikarin.componentModel

import kotlin.reflect.KType
import kotlin.reflect.typeOf

class CharConverter : TypeConverter() {
    override fun canConvertFrom(sourceType: KType): Boolean =
        sourceType == typeOf<String>() || super.canConvertFrom(sourceType)

    override fun convertTo(value: Any?, destinationType: KType): Any? {
        if (destinationType == typeOf<String>() && value is Char) {
            if (value == '\u0000') {
                return ""
            }
        }

        return super.convertTo(value, destinationType)
    }

    override fun convertFrom(value: Any): Any? {
        if (value is String) {
            var text = value

            if (text.length > 1) {
                text = text.trim()
            }

            if (text.length == 0) {
                return '\u0000'
            } else if (text.length == 1) {
                return text[0]
            }

            throw Exception("format exception TODO")
        }

        return super.convertFrom(value)
    }
}