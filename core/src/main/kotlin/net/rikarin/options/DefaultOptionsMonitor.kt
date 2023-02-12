package net.rikarin.options

import net.rikarin.Disposable
import net.rikarin.primitives.ChangeToken

class DefaultOptionsMonitor<TOptions : Any>(
    private val factory: OptionsFactory<TOptions>,
    val sources: Iterable<OptionsChangeTokenSource<TOptions>>,
    private val cache: OptionsMonitorCache<TOptions>
) : OptionsMonitor<TOptions>, Disposable {
    private val _registrations = mutableListOf<Disposable>()
    // TODO: event?

    init {
        for (source in sources) {
            _registrations.add(ChangeToken.onChange(source::getChangeToken) { invokeChanged(source.name) })
        }
    }

    // TODO: finish this class
    override val currentValue: TOptions get() = get(Options.DEFAULT_NAME)

    override fun get(name: String?): TOptions {
        TODO("Not yet implemented")
    }

    override fun onChange(listener: (TOptions, String?) -> Unit): Disposable {
        TODO("Not yet implemented")
    }

    override fun dispose() {
        for (registration in _registrations) {
            registration.dispose()
        }

        _registrations.clear()
    }

    private fun invokeChanged(name: String?) {
        val n = name ?: Options.DEFAULT_NAME
        cache.tryRemove(name)
        val options = get(name)
        // TODO: invoke
    }
}