package net.rikarin.dependencyInjeciton

interface ServiceScope : AutoCloseable {
    val serviceProvider: ServiceProvider
}