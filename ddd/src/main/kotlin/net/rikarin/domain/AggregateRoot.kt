package net.rikarin.domain

interface AggregateRoot<T: Any> : UntypedAggregateRoot, Entity<T>