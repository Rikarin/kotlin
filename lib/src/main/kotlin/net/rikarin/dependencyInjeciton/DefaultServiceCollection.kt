package net.rikarin.dependencyInjeciton

import net.rikarin.InvalidOperationException

class DefaultServiceCollection : ServiceCollection {
    private val _descriptors = mutableListOf<ServiceDescriptor>()
    var isReadOnly = false
        private set

    fun makeReadOnly() {
        isReadOnly = true
    }

    override val size: Int get() = _descriptors.size

    override fun add(element: ServiceDescriptor): Boolean {
        checkReadOnly()
        return _descriptors.add(element)
    }

    override fun add(index: Int, element: ServiceDescriptor) {
        checkReadOnly()
        _descriptors.add(index, element)
    }

    override fun addAll(index: Int, elements: Collection<ServiceDescriptor>): Boolean {
        checkReadOnly()
        return _descriptors.addAll(index, elements)
    }

    override fun addAll(elements: Collection<ServiceDescriptor>): Boolean {
        checkReadOnly()
        return _descriptors.addAll(elements)
    }

    override fun clear() {
        checkReadOnly()
        _descriptors.clear()
    }

    override fun remove(element: ServiceDescriptor): Boolean {
        checkReadOnly()
        return _descriptors.remove(element)
    }

    override fun removeAll(elements: Collection<ServiceDescriptor>): Boolean {
        checkReadOnly()
        return _descriptors.removeAll(elements)
    }

    override fun removeAt(index: Int): ServiceDescriptor {
        checkReadOnly()
        return _descriptors.removeAt(index)
    }

    override fun retainAll(elements: Collection<ServiceDescriptor>): Boolean {
        checkReadOnly()
        return _descriptors.retainAll(elements)
    }

    override fun set(index: Int, element: ServiceDescriptor): ServiceDescriptor {
        checkReadOnly()
        return _descriptors.set(index, element)
    }

    override fun contains(element: ServiceDescriptor) = _descriptors.contains(element)
    override fun containsAll(elements: Collection<ServiceDescriptor>) = _descriptors.containsAll(elements)
    override fun get(index: Int) = _descriptors.get(index)
    override fun indexOf(element: ServiceDescriptor) = _descriptors.indexOf(element)
    override fun isEmpty() = _descriptors.isEmpty()
    override fun iterator() = _descriptors.iterator()
    override fun lastIndexOf(element: ServiceDescriptor) = _descriptors.lastIndexOf(element)
    override fun listIterator() = _descriptors.listIterator()
    override fun listIterator(index: Int) = _descriptors.listIterator(index)
    override fun subList(fromIndex: Int, toIndex: Int) = _descriptors.subList(fromIndex, toIndex)

    private fun checkReadOnly() {
        if (isReadOnly) {
            throw InvalidOperationException()
        }
    }
}