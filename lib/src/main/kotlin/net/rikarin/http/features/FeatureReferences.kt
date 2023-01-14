package net.rikarin.http.features

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty
import kotlin.reflect.full.memberProperties

class FeatureReferences<TCache : Any>(collection: FeatureCollection, internal val factory: () -> TCache, revision: Int? = null) {
    var revision: Int = 0
        internal set

    var collection: FeatureCollection
        private set

    var cache: TCache = factory()

    init {
        this.collection = collection
        this.revision = revision ?: collection.revision
    }

    internal inline fun <TInstance, reified TProperty> byProperty(propertyName: String, crossinline factory: () -> TProperty): ReadWriteProperty<TInstance, TProperty> {
        val self = this

        return object : ReadWriteProperty<TInstance, TProperty> {
            override fun getValue(thisRef: TInstance, property: KProperty<*>): TProperty {
                val prop = self.cache::class.memberProperties.find { it.name == propertyName }
                val revision = collection.revision ?: throw Exception("disposed")

                if (revision != self.revision) {
                    if (prop is KMutableProperty<*>) {
                        prop.setter.call(self.cache, null)
                    }
                }

                return (prop?.getter?.call(self.cache) ?: updateCached(revision)) as TProperty
            }

            override fun setValue(thisRef: TInstance, property: KProperty<*>, value: TProperty) {
                val prop = self.cache::class.memberProperties.find { it.name == propertyName }
                if (prop is KMutableProperty<*>) {
                    prop.setter.call(self, value)
                }
            }

            private fun updateCached(revision: Int): TProperty {
                if (revision != self.revision) {
                    self.cache = self.factory()
                }

                var cached = self.collection.get<TProperty?>()
                if (cached == null) {
                    cached = factory()
                    self.collection.set(cached)
                    self.revision = self.collection.revision
                }

                self.revision = revision

                val prop = self.cache::class.memberProperties.find { it.name == propertyName }
                if (prop is KMutableProperty<*>) {
                    prop.setter.call(self.cache, cached)
                }

                return prop?.getter?.call(self.cache) as TProperty
            }
        }
    }
}