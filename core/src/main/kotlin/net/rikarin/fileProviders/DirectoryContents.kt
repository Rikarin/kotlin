package net.rikarin.fileProviders

interface DirectoryContents : Iterable<FileInfo> {
    val exists: Boolean
}