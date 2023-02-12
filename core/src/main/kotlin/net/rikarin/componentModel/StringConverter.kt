package net.rikarin.componentModel

import kotlin.reflect.KType
import kotlin.reflect.typeOf

class StringConverter : TypeConverter() {
    override fun canConvertFrom(sourceType: KType): Boolean =
        sourceType == typeOf<String>() || super.canConvertFrom(sourceType)

    override fun convertFrom(value: Any): Any? {
        if (value is String) {
            return value
        }

        // TODO: check this
//        if (value == null) {
//            return ""
//        }

        return super.convertFrom(value)
    }
}