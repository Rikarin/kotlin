package net.rikarin

inline fun <reified T> nameOf() = T::class.simpleName
