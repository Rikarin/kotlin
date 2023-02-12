package net.rikarin.configuration

import net.rikarin.configuration.implementation.DefaultConfigurationBuilder
import net.rikarin.configuration.yaml.YamlConfigurationFileParser
import net.rikarin.configuration.yaml.addYamlFile
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class YamlConfigurationTest {
    @Test
    fun createConfiguration() {
        val configurationBuilder = DefaultConfigurationBuilder()
        configurationBuilder.addYamlFile("src/test/kotlin/net/rikarin/configuration/config.yaml")
        val configuration = configurationBuilder.build()

        assertEquals("elasticsearch", configuration.getSection("metadata:name").value)

//        val foo = configuration["foo"]
//        println("value $foo")
    }

    @Test
    fun testParserWithEmptyValue() {
        val parser = YamlConfigurationFileParser()
        parser.parse("".byteInputStream())
    }

    @Test
    fun testParser() {
        val parser = YamlConfigurationFileParser()
        val output = parser.parse(yamlContent.byteInputStream())
        println("output $output")
    }

    private val yamlContent = """
apiVersion: v1
foo: null
kind: Service
metadata:
  name: kibana
  namespace: logging
spec:
  selector:
    app: kibana
  ports:
  - protocol: TCP
    port: 5601
    targetPort: 5601
"""
}