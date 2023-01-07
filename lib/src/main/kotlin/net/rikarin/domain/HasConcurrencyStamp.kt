package net.rikarin.domain

interface HasConcurrencyStamp {
    val concurrencyStamp: String?
}