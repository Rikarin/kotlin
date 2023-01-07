package net.rikarin.domain

interface TypedAggregateRoot<T: Any> : AggregateRoot, TypedEntity<T> {

}