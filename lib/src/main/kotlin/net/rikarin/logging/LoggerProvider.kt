package net.rikarin.logging

interface LoggerProvider {
    fun createLogger(categoryName: String): Logger
}