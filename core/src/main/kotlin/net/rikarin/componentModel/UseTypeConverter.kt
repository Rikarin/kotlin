package net.rikarin.componentModel

import kotlin.reflect.KClass

annotation class UseTypeConverter(val converter: KClass<*>)