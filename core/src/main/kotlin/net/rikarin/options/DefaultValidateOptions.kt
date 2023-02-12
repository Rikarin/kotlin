package net.rikarin.options

class DefaultValidateOptions<TOptions : Any>(
    val name: String?,
    val validation: (TOptions) -> Boolean,
    val failureMessage: String
) : ValidateOptions<TOptions> {
    override fun validate(name: String?, options: TOptions): ValidateOptionsResult {
        if (this.name == null || this.name == name) {
            if (validation(options)) {
                return ValidateOptionsResult.SUCCESS
            }

            return ValidateOptionsResult.fail(failureMessage)
        }

        return ValidateOptionsResult.SKIP
    }
}