package net.rikarin.dependencyInjeciton

import net.rikarin.Disposable

interface ServiceScope : Disposable {
    val serviceProvider: ServiceProvider
}
