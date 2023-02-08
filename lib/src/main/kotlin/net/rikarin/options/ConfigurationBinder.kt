package net.rikarin.options

import net.rikarin.componentModel.TypeDescriptor
import net.rikarin.configuration.*
import kotlin.reflect.*
import kotlin.reflect.full.createType
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.withNullability

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
    val configValue = section?.value
//    println("value $configValue, ${section.path}")

    if (configValue != null) {
        val converted = tryConvertValue(type, configValue, section?.path)

        bindingPoint.trySetValue(converted)
        return
    }

    if (config.children.any()) {
//        println("has children binding point ${bindingPoint.value}")
        // TODO: stuff



        // TODO: dictionary stuff
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

    // TODO: error on unknown configuration

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

private fun tryConvertValue(type: KType, value: String, path: String?): Any? {
    if (type == typeOf<Any>()) {
        return value
    }

    if (type.isMarkedNullable) {
        if (value.isEmpty()) {
            return value
        }

        return tryConvertValue(type.withNullability(false), value, path)
    }

    val converter = TypeDescriptor.getConverter(type)
    if (converter.canConvertFrom(typeOf<String>())) {
//        try {
        return converter.convertFromString(value)

//        }
    }

    // TODO: converter
    return value


//    return nulll
}