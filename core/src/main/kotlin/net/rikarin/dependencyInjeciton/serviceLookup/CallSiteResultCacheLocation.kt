package net.rikarin.dependencyInjeciton.serviceLookup

internal enum class CallSiteResultCacheLocation {
    ROOT, SCOPE, DISPOSE, NONE;

    companion object {
        fun getCommonCacheLocation(first: CallSiteResultCacheLocation, second: CallSiteResultCacheLocation): CallSiteResultCacheLocation {
            if (first == NONE || second == NONE) return NONE
            if (first == DISPOSE || second == DISPOSE) return DISPOSE
            if (first == SCOPE || second == SCOPE) return SCOPE

            return ROOT
        }
    }
}