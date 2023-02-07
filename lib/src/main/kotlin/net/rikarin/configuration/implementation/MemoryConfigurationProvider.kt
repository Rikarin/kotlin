package net.rikarin.configuration.implementation

class MemoryConfigurationProvider(source: MemoryConfigurationSource) :
    DefaultConfigurationProvider(),
    Iterable<Map.Entry<String, String?>> {

    init {
        if (source.initialData != null) {
            data.putAll(source.initialData)
        }
    }

    override fun iterator() = data.iterator()
}