package net.rikarin.testApp

import net.rikarin.builder.WebApplication
import net.rikarin.configuration.yaml.addYamlFile
import net.rikarin.dependencyInjeciton.buildServiceProvider
import net.rikarin.dependencyInjeciton.getService
import net.rikarin.options.Options
import net.rikarin.options.configure


fun main() {
    println("Hello World!")
    val builder = WebApplication.createBuilder()
    builder.configuration.addYamlFile("config.yaml")

    builder.services.configure<Metadata>(builder.configuration.getSection("metadata"))


    val metadata = builder.configuration.getSection("metadata")
    println("metadata ${metadata["name"]}")

    val provider = builder.services.buildServiceProvider()
    val metadataResolved = provider.getService<Options<Metadata>>()

    println("metadata ${metadataResolved!!.value.name}")

//        builder.services.add()

//        val app = builder.build()
    // app.use...

//    runBlocking {
//            app.run()
//    }
}

class Metadata {
    lateinit var name: String
}