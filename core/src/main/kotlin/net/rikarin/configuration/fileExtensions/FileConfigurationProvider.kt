package net.rikarin.configuration.fileExtensions

import net.rikarin.Disposable
import net.rikarin.configuration.implementation.DefaultConfigurationProvider
import java.io.InputStream

abstract class FileConfigurationProvider(val source: FileConfigurationSource) : DefaultConfigurationProvider(), Disposable {
    private val _changeTokenRegistration: Disposable? = null

    override fun load() {
        load(false)
    }

    abstract fun load(stream: InputStream)

    private fun load(reload: Boolean) {

        // TODO: fix this
        val stream = source.file?.inputStream()
        if (stream != null) {
            load(stream)
        }




//        onReload()
    }

    override fun dispose() {
        _changeTokenRegistration?.dispose()
    }
}