package net.rikarin.hosting

import java.io.File

interface WebHostEnvironment : HostEnvironment {
    var webRootPath: String
    var webRootFileProvider: File
}