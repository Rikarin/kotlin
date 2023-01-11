package net.rikarin.http

interface Session {
    val isAvailable: Boolean
    val id: String
    val keys: Iterable<String>

    fun load()
    fun commit()
    fun getOrNull(key: String): Array<Byte>
    fun set(key: String, value: Array<Byte>)
    fun remove(key: String)
    fun clear()
}