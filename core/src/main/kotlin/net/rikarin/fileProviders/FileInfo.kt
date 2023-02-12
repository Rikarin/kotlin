package net.rikarin.fileProviders

import java.io.InputStream
import java.util.*

interface FileInfo {
    val exists: Boolean
    val length: Long
    val physicalPath: String?
    val name: String
    val lastModified: Date // TODO: not sure about this
    val isDirectory: Boolean

    fun createReadStream(): InputStream
}