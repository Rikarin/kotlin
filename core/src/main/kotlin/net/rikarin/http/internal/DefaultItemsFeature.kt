package net.rikarin.http.internal

import net.rikarin.http.features.ItemsFeature

internal class DefaultItemsFeature : ItemsFeature {
    override var items: MutableMap<Any, Any?> = mutableMapOf()
}
