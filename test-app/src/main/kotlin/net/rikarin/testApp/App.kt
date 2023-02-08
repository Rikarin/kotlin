package net.rikarin.testApp

import net.rikarin.builder.WebApplication
import net.rikarin.configuration.yaml.addYamlFile
import net.rikarin.dependencyInjeciton.buildServiceProvider
import net.rikarin.dependencyInjeciton.getRequiredService
import net.rikarin.options.Options
import net.rikarin.options.configure

fun main() {
    val builder = WebApplication.createBuilder()

    builder.configuration.addYamlFile("config.yaml")
    builder.services.configure<Metadata>(builder.configuration.getSection("metadata"))
    builder.services.configure<Spec>(builder.configuration.getSection("spec"))

    // Fake
    val provider = builder.services.buildServiceProvider()
    val metadataResolved = provider.getRequiredService<Options<Metadata>>()
    println("metadata ${metadataResolved.value.name} ${metadataResolved.value.namespace}")

    val specs = provider.getRequiredService<Options<Spec>>()
    println("specs ${specs.value.replicas} ${specs.value.foobar}")

//        val app = builder.build()
    // app.use...

//    runBlocking {
//            app.run()
//    }
}

class Metadata {
    var name: String? = null
    lateinit var namespace: String
}

class Spec {
    var replicas: Int? = null
    var foobar: Double? = null
}