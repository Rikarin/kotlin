package net.rikarin.options

interface ValidateOptions<TOptions : Any> {
    fun validate(name: String?, options: TOptions): ValidateOptionsResult
}