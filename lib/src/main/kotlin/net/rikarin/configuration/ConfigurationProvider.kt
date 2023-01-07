package net.rikarin.configuration

interface ConfigurationProvider {
    val reloadtoken: ChangeToken

//    fun tryGet(key: String, out value: String?): Boolean
    // maybe rename this to get and use it as an operator
    fun getOrNull(key: String): String?
    fun set(key: String, value: String?)
    fun load()

    fun getChildKeys(earlierKeys: Iterable<String>, parentPath: String?): Iterable<String>
//    fun getChildKeys(parentPath: String?): Iterable<String>
}