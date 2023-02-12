package net.rikarin.options

import net.rikarin.FAILED_BINDING
import net.rikarin.InvalidOperationException
import net.rikarin.componentModel.TypeDescriptor
import net.rikarin.configuration.*
import kotlin.reflect.*
import kotlin.reflect.full.createType
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.withNullability

inline fun <reified T> Configuration.get(): T? = get<T> { }

inline fun <reified T> Configuration.get(noinline configureOptions: BinderOptionsAction?): T? =
    get(typeOf<T>(), configureOptions) as T?

fun Configuration.get(type: KType): Any? = get(type) { }

fun Configuration.get(type: KType, configureOptions: BinderOptionsAction?): Any? {
    val options = BinderOptions()
    configureOptions?.invoke(options)
    val bindingPoint = BindingPoint()

    bindInstance(type, bindingPoint, this, options)
    return bindingPoint.value
}

fun Configuration.bind(instance: Any?, configureOptions: BinderOptionsAction?) {
    if (instance != null) {
        val options = BinderOptions()
        configureOptions?.invoke(options)

        val bindingPoint = BindingPoint(instance, true)
        bindInstance(instance::class.createType(), bindingPoint, this, options)
    }
}



private fun bindInstance(
    type: KType,
    bindingPoint: BindingPoint,
    config: Configuration,
    options: BinderOptions
) {
    if (type == typeOf<ConfigurationSection>()) {
        bindingPoint.trySetValue(config)
        return
    }

    val section = config as ConfigurationSection
    val configValue = section.value
    if (configValue != null) {
        val (converted, value) = tryConvertValue(type, configValue, section.path)
        if (converted) {
            bindingPoint.trySetValue(value)
            return
        }
    }

    if (config.children.any()) {
        // TODO: Array, List, Set, Map...

        if (false) {

        } else {
            if (false) {

            } else {
                bindProperties(bindingPoint.value!!, config, options)
            }
        }
    }
}


private fun bindProperties(instance: Any, configuration: Configuration, options: BinderOptions) {
    val modelProperties = getAllProperties(instance)

    if (options.errorOnUnknownConfiguration) {
        TODO() // TODO: finish this
    }

    for (property in modelProperties) {
        bindProperty(property, instance, configuration, options)
    }
}

private fun bindProperty(property: KProperty<*>, instance: Any, config: Configuration, options: BinderOptions) {
    // TODO: some checks

    val propertyBindingPoint = BindingPoint(
        { property.getter.call(instance) },
        property !is KMutableProperty<*> // TODO: check for others
    )

    bindInstance(
        property.returnType,
        propertyBindingPoint,
        config.getSection(getPropertyName(property)),
        options
    )

    if (propertyBindingPoint.hasNewValue) {
        (property as? KMutableProperty<*>)?.setter?.call(instance, propertyBindingPoint.value!!)
    }
}

private fun getPropertyName(property: KProperty<*>): String {
    val annotation = property.findAnnotation<ConfigurationKeyName>()
    if (!annotation?.name.isNullOrEmpty()) {
        return annotation!!.name
    }

    return property.name
}

private fun getAllProperties(instance: Any) =
    instance::class.memberProperties.filter {
        it.visibility == KVisibility.PUBLIC && it is KMutableProperty<*>
    } as List<KProperty<*>>

private fun tryConvertValue(type: KType, value: String, path: String?): Pair<Boolean, Any?> {
    if (type == typeOf<Any>()) {
        return Pair(true, value)
    }

    if (type.isMarkedNullable) {
        if (value.isEmpty()) {
            return Pair(true, null)
        }

        return tryConvertValue(type.withNullability(false), value, path)
    }

    val converter = TypeDescriptor.getConverter(type)
    if (converter.canConvertFrom(typeOf<String>())) {
        try {
            return Pair(true, converter.convertFromString(value))
        } catch (e: Exception) {
            throw InvalidOperationException(FAILED_BINDING.format(path, type, e))
        }
    }

    if (type == typeOf<ByteArray>()) {
        TODO()
    }

    return Pair(false, null)
}