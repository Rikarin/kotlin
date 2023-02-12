package net.rikarin.domain

interface HasConcurrencyStamp {
    var concurrencyStamp: String?
}