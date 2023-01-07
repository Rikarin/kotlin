package net.rikarin.configuration.implementation

import net.rikarin.configuration.ConfigurationBuilder
import net.rikarin.configuration.ConfigurationPath
import net.rikarin.configuration.ConfigurationRoot
import java.util.*

fun ConfigurationBuilder.addInMemoryCollection(initialData: Map<String, String?>? = null) {
    add(MemoryConfigurationSource(initialData))
}


internal fun ConfigurationRoot.getChildrenImplementation(path: String?) =
    providers
        .fold(mutableListOf<String>() as Iterable<String>) { seed, source -> source.getChildKeys(seed, path) }
        .distinctBy { it.lowercase(Locale.getDefault()) }
        .map { getSection(if (path == null) it else ConfigurationPath.combine(path, it)) }
        .toList()


//    internal static IEnumerable<IConfigurationSection> GetChildrenImplementation(this IConfigurationRoot root, string? path)
//    {
//        using ReferenceCountedProviders? reference = (root as ConfigurationManager)?.GetProvidersReference();
//        IEnumerable<IConfigurationProvider> providers = reference?.Providers ?? root.Providers;
//
//        IEnumerable<IConfigurationSection> children = providers
//                .Aggregate(Enumerable.Empty<string>(),
//            (seed, source) => source.GetChildKeys(seed, path))
//        .Distinct(StringComparer.OrdinalIgnoreCase)
//        .Select(key => root.GetSection(path == null ? key : ConfigurationPath.Combine(path, key)));
//
//        if (reference is null)
//        {
//            return children;
//        }
//        else
//        {
//            // Eagerly evaluate the IEnumerable before releasing the reference so we don't allow iteration over disposed providers.
//            return children.ToList();
//        }
//    }
//}