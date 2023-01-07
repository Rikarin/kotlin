package net.rikarin.configuration

object ConfigurationPath {
    const val KEY_DELIMITER = ":"

    fun combine(vararg pathSegments: String) = pathSegments.joinToString(KEY_DELIMITER)

    fun getSectionKey(path: String): String {
//        if (path.isNullOrEmpty()) {
//            return path
//        }

        val lastDelimiterIndex = path.lastIndexOf(KEY_DELIMITER)
        return if (lastDelimiterIndex == -1) path else path.substring(lastDelimiterIndex + 1)
    }
}