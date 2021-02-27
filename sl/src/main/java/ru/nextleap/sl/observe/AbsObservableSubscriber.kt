package ru.nextleap.sl.observe

import ru.nextleap.sl.IProvider
import ru.nextleap.sl.lifecycle.Lifecycle
import ru.nextleap.sl.provider.IObservableSubscriber
import ru.nextleap.sl.provider.ObservableUnion

abstract class AbsObservableSubscriber : IObservableSubscriber {
    override fun getState(): Int {
        return Lifecycle.VIEW_READY
    }

    override fun setState(state: Int) = Unit

    override fun getProviderSubscription(): List<String> {
        return listOf(ObservableUnion.NAME)
    }

    override fun isValid(): Boolean {
        return true
    }

    override fun onStopProvider(provider: IProvider) = Unit

    override fun stop() = Unit

}
