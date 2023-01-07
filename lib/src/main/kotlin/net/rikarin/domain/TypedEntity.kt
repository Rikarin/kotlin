package net.rikarin.domain

interface TypedEntity<T: Any> {
    val id: T
}