package net.rikarin.builder

data class WebApplicationOptions(
    val args: Array<String>? = null,
    val environmentName: String? = null,
    val applicationName: String? = null,
    val contentRootPath: String? = null,
    val webRootPath: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WebApplicationOptions

        if (args != null) {
            if (other.args == null) return false
            if (!args.contentEquals(other.args)) return false
        } else if (other.args != null) return false
        if (environmentName != other.environmentName) return false
        if (applicationName != other.applicationName) return false
        if (contentRootPath != other.contentRootPath) return false
        if (webRootPath != other.webRootPath) return false

        return true
    }

    override fun hashCode(): Int {
        var result = args?.contentHashCode() ?: 0
        result = 31 * result + (environmentName?.hashCode() ?: 0)
        result = 31 * result + (applicationName?.hashCode() ?: 0)
        result = 31 * result + (contentRootPath?.hashCode() ?: 0)
        result = 31 * result + (webRootPath?.hashCode() ?: 0)
        return result
    }
}