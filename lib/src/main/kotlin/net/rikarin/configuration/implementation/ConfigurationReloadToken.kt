package net.rikarin.configuration.implementation

import io.reactivex.rxjava3.subjects.PublishSubject
import net.rikarin.Disposable
import net.rikarin.primitives.ChangeToken

class ConfigurationReloadToken : ChangeToken {
    private val _observable = PublishSubject.create<Unit>()
    override val hasChanged: Boolean
        get() = _observable.hasComplete()
    override val activeChangeCallbacks = true

    override fun registerChangeCallback(callback: () -> Unit): Disposable {
        val subscription = _observable.subscribe { callback() }
        return Disposable { subscription.dispose() }
    }

    fun onReload() {
        _observable.onNext(Unit)
        _observable.onComplete()
    }
}
