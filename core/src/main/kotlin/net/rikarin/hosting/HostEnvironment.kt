package net.rikarin.hosting

import net.rikarin.fileProviders.FileProvider

interface HostEnvironment {
    var environmentName: String
    var applicationName: String
    var contentRootPath: String
    var contentRootFileProvider: FileProvider
}