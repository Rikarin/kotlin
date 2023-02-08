package net.rikarin.configuration

import net.rikarin.configuration.implementation.MemoryConfigurationSource
import java.util.*

fun ConfigurationBuilder.addInMemoryCollection(initialData: Map<String, String?>? = null): ConfigurationBuilder {
    add(MemoryConfigurationSource(initialData))
    return this
}

fun ConfigurationBuilder.addInMemoryCollection(initialData: Iterable<Pair<String, String?>>?): ConfigurationBuilder {
    // TODO: store iterable instead of map
    add(MemoryConfigurationSource(initialData?.toMap()))
    return this
}

internal fun ConfigurationRoot.getChildrenImplementation(path: String?): List<ConfigurationSection> {
    val reference = (this as? ConfigurationManager)?.getProvidersReference()
    val providers = reference?.providers ?: this.providers

    val ret = providers
        .fold(mutableListOf<String>() as Iterable<String>) { seed, source -> source.getChildKeys(seed, path) }
        .distinctBy { it.lowercase(Locale.getDefault()) }
        .map { getSection(if (path == null) it else ConfigurationPath.combine(path, it)) }
        .toList()

    reference?.dispose()
    return ret
}