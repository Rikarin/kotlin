package net.rikarin.options

import net.rikarin.createInstance
import kotlin.reflect.KType

class DefaultOptionsFactory<TOptions : Any>(
    private val self: KType,
    setups: Iterable<ConfigureOptions<TOptions>>,
    postConfigures: Iterable<PostConfigureOptions<TOptions>>,
    validations: Iterable<ValidateOptions<TOptions>>
) : OptionsFactory<TOptions> {
    private val _setups: List<ConfigureOptions<TOptions>>
    private val _postConfigures: List<PostConfigureOptions<TOptions>>
    private val _validations: List<ValidateOptions<TOptions>>

    constructor(
        type: KType,
        setups: Iterable<ConfigureOptions<TOptions>>,
        postConfigures: Iterable<PostConfigureOptions<TOptions>>
    ) : this(type, setups, postConfigures, listOf())

    init {
        _setups = setups.toList()
        _postConfigures = postConfigures.toList()
        _validations = validations.toList()
    }

    override fun create(name: String): TOptions {
        val options = createInstance(name)

        for (setup in _setups) {
            if (setup is ConfigureNamedOptions<TOptions>) {
                setup.configure(name, options)
            } else if (name == Options.DEFAULT_NAME) {
                setup.configure(options)
            }
        }

        for (post in _postConfigures) {
            post.postConfigure(name, options)
        }

        if (_validations.isNotEmpty()) {
            val failures = mutableListOf<String>()

            for (validate in _validations) {
                val result = validate.validate(name, options)
                if (result.failed) {
                    failures.addAll(result.failures!!)
                }
            }

            if (failures.isNotEmpty()) {
                throw OptionsValidationException(name, self, failures)
            }
        }

        return options
    }

    protected fun createInstance(name: String): TOptions {
        val type = self.arguments[0].type!!
        return type.createInstance() as TOptions
    }
}