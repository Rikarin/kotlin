package net.rikarin.configuration.yaml

import com.charleskorn.kaml.*
import net.rikarin.InvalidOperationException
import net.rikarin.configuration.ConfigurationPath
import java.io.InputStream

internal class YamlConfigurationFileParser {
    private val _data = sortedMapOf<String, String>() // TODO: ignore case

    fun parse(input: InputStream): Map<String, String> {
        _data.clear()

        try {
            val node = Yaml.default.parseToYamlNode(input)
            visitYamlMap(listOf(), node.yamlMap)
        } catch (_: EmptyYamlDocumentException) {

        }

        return _data
    }

    private fun visitValue(path: String, value: String?) {
        if (_data.containsKey(path)) {
            throw InvalidOperationException("duplicate key found $path")
        }

        _data[path] = value
    }

    private fun visitYamlMap(previousContext: List<String>, map: YamlMap) {
        for (node in map.entries) {
            visitYamlNode(previousContext, node.key.content, node.value)
        }
    }

    private fun visitYamlNode(previousContext: List<String>, context: String, node: YamlNode) {
        val newContext = listOf(*previousContext.toTypedArray(), context)
        val currentPath = ConfigurationPath.combine(*newContext.toTypedArray())

        when (node) {
            is YamlMap -> {
                visitYamlMap(newContext, node)
            }

            is YamlList -> {
                visitYamlList(newContext, node)
            }

            is YamlScalar -> {
                visitValue(currentPath, node.content)
            }

            is YamlNull -> {
                visitValue(currentPath, null)
            }

            else -> throw InvalidOperationException("unknown yaml node")
        }
    }

    private fun visitYamlList(previousContext: List<String>, list: YamlList) {
        for (i in 0 until list.items.size) {
            visitYamlNode(previousContext, i.toString(), list[i])
        }
    }
}