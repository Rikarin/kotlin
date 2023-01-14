package net.rikarin.dependencyInjeciton

interface ServiceScopeFactory {
    fun createScope(): ServiceScope
}