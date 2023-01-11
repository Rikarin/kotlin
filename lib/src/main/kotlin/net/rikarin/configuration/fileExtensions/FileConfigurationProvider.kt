package net.rikarin.configuration.fileExtensions

import net.rikarin.configuration.implementation.DefaultConfigurationProvider
import java.io.InputStream

abstract class FileConfigurationProvider(val source: FileConfigurationSource) : DefaultConfigurationProvider(), AutoCloseable {
    private val _changeTokenRegistration: AutoCloseable? = null

    override fun load() {
        load(false)
    }

    abstract fun load(stream: InputStream)

    private fun load(reload: Boolean) {

        // TODO: fix this
        println("file ${source.file}")
        val stream = source.file?.inputStream()
        if (stream != null) {
            load(stream)
        }




//        onReload()
    }

    override fun close() {
        _changeTokenRegistration?.close()
    }
}