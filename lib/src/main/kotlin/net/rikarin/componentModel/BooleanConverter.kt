package net.rikarin.componentModel

import kotlin.reflect.KType
import kotlin.reflect.typeOf

class BooleanConverter : TypeConverter() {
    override fun canConvertFrom(sourceType: KType): Boolean =
        sourceType == typeOf<String>() || super.canConvertFrom(sourceType)

    override fun convertFrom(value: Any): Any? {
        if (value is String) {
            // TODO: try/catch
            return value.trim().toBoolean()
        }

        return super.convertFrom(value)
    }
}