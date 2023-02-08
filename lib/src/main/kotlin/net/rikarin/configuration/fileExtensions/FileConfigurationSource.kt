package net.rikarin.configuration.fileExtensions

import net.rikarin.configuration.ConfigurationSource
import net.rikarin.fileProviders.FileProvider
import java.io.File

abstract class FileConfigurationSource : ConfigurationSource {
    // we need to create FileProvider to be able to abstract usage of memory vs file sources
    var file: File? = null // TODO: remove this
    var fileProvider: FileProvider? = null

//    public Action<FileLoadExceptionContext>? OnLoadException { get; set; }

    var path: String? = null
    var optional: Boolean = false
    var reloadOnChange: Boolean = false
    var reloadDelay = 250

//    public void EnsureDefaults(IConfigurationBuilder builder)
//    {
//        FileProvider ??= builder.GetFileProvider();
//        OnLoadException ??= builder.GetFileLoadExceptionHandler();
//    }

    fun resolveFileProvider() {
        if (file == null && !path.isNullOrEmpty()) {
            // TODO: fix this
            file = File(path!!)
        }
    }
}

//    public void ResolveFileProvider()
//    {
//        if (FileProvider == null &&
//            !string.IsNullOrEmpty(Path) &&
//            System.IO.Path.IsPathRooted(Path))
//        {
//            string? directory = System.IO.Path.GetDirectoryName(Path);
//            string? pathToFile = System.IO.Path.GetFileName(Path);
//            while (!string.IsNullOrEmpty(directory) && !Directory.Exists(directory))
//            {
//                pathToFile = System.IO.Path.Combine(System.IO.Path.GetFileName(directory), pathToFile);
//                directory = System.IO.Path.GetDirectoryName(directory);
//            }
//            if (Directory.Exists(directory))
//            {
//                FileProvider = new PhysicalFileProvider(directory);
//                Path = pathToFile;
//            }
//        }
//    }