package net.rikarin.configuration.implementation

class MemoryConfigurationProvider(private val source: MemoryConfigurationSource) :
    DefaultConfigurationProvider(),
    Iterable<Map.Entry<String, String?>> {

    init {
        if (source.initialData != null) {
            data.putAll(source.initialData)
        }
    }

    override fun iterator() = data.iterator()
}