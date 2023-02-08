package net.rikarin.configuration

class BindingPoint {
    private val _initialValueProvider: (() -> Any?)?
    private var _initialValue: Any? = null
    private var _setValue: Any? = null
    private var _valueSet = false

    val isReadOnly: Boolean

    val value: Any?
        get() {
            if (_valueSet) {
                return _setValue
            }

            if (_initialValue == null) {
                _initialValue = _initialValueProvider?.invoke()
            }

            return _initialValue
        }

    val hasNewValue: Boolean
        get() {
            if (isReadOnly) {
                return false
            }

            if (_valueSet) {
                return true
            }

            return false
        }

    constructor(initialValue: Any? = null, isReadOnly: Boolean = false) {
        _initialValue = initialValue
        this.isReadOnly = isReadOnly
        _initialValueProvider = null
    }

    constructor(initialValueProvider: () -> Any?, isReadOnly: Boolean) {
        this.isReadOnly = isReadOnly
        _initialValueProvider = initialValueProvider
    }

    fun setValue(newValue: Any?) {
        assert(!isReadOnly)
        assert(!_valueSet)

        _setValue = newValue
        _valueSet = true
    }

    fun trySetValue(newValue: Any?) {
        if (!isReadOnly) {
            setValue(newValue)
        }
    }
}