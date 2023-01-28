package net.rikarin.options

open class ValidateOptionsResult {
    var succeeded: Boolean = false
        protected set

    var skipped: Boolean = false
        protected set

    var failed: Boolean = false
        protected set

    var failureMessage: String? = null
        protected set

    var failures: Iterable<String>? = null
        protected set

    companion object {
        val SKIP = ValidateOptionsResult().apply { skipped = true }
        val SUCCESS = ValidateOptionsResult().apply { succeeded = true }

        fun fail(failureMessage: String) =
            ValidateOptionsResult().apply {
                failed = true
                this.failureMessage = failureMessage
                failures = listOf(failureMessage)
            }

        fun fail(failures: Iterable<String>) =
            ValidateOptionsResult().apply {
                failed = true
                failureMessage = failures.joinToString("; ")
                this.failures = failures
            }
    }
}