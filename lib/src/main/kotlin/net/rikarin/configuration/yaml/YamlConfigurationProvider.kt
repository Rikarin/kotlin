package net.rikarin.configuration.yaml

import net.rikarin.configuration.fileExtensions.FileConfigurationProvider
import java.io.InputStream

class YamlConfigurationProvider(source: YamlConfigurationSource) : FileConfigurationProvider(source) {
    override fun load(stream: InputStream) {
        val parser = YamlConfigurationFileParser()

//        try {
            data.putAll(parser.parse(stream))
//        } catch (e: Throwable) { // YamlException
//            throw new FormatException(Resources.FormatError_YamlParseError(e.Message), e);
//        }
    }
}
