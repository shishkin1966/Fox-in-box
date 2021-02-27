package ru.nextleap.sl

abstract class AbsProvider : IProvider {
    override fun isPersistent(): Boolean {
        return false
    }

    override fun isValid(): Boolean {
        return true
    }

    override fun onUnRegister() = Unit

    override fun onRegister() = Unit

    override fun stop() = Unit
}
