package net.rikarin.componentModel

import kotlin.reflect.KType
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.typeOf

object TypeDescriptor {
    // This is minimal bare-bone implementation to support TypeConverter
    fun getConverter(type: KType): TypeConverter {
        val annotation = type.findAnnotation<UseTypeConverter>()
        if (annotation != null) {
            return annotation.converter.createInstance() as TypeConverter
        }

        return when (type) {
            typeOf<Byte>() -> ByteConverter()
            typeOf<UByte>() -> UByteConverter()
            typeOf<Short>() -> ShortConverter()
            typeOf<UShort>() -> UShortConverter()
            typeOf<Int>() -> IntConverter()
            typeOf<UInt>() -> UIntConverter()
            typeOf<Long>() -> LongConverter()
            typeOf<ULong>() -> ULongConverter()
            typeOf<Float>() -> FloatConverter()
            typeOf<Double>() -> DoubleConverter()

            typeOf<Boolean>() -> BooleanConverter()
            typeOf<Char>() -> CharConverter()
            typeOf<String>() -> TypeConverter()
            else -> throw Exception("not found TODO")
        }
    }
}