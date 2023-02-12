package net.rikarin.fileProviders

import net.rikarin.primitives.ChangeToken

interface FileProvider {
    fun getFileInfo(subPath: String): FileInfo
    fun getDirectoryContents(subPath: String): DirectoryContents
    fun watch(filter: String): ChangeToken
}