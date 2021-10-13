package ru.nextleap.sl.observe

import ru.nextleap.common.Debounce
import ru.nextleap.sl.provider.ApplicationProvider
import ru.nextleap.sl.provider.IObservableUnion
import ru.nextleap.sl.provider.ObservableUnion

class ObservableDebounce(val obj: String, delay: Long) : Debounce(delay, 0) {
    override fun run() {
        val provider =
            ApplicationProvider.serviceLocator?.get(ObservableUnion.NAME) as IObservableUnion?
        val objectObservableProvider = provider?.getObservable(ObjectObservable.NAME) as ObjectObservable?
        val observers = objectObservableProvider?.getObserverNames(obj)
        if (observers != null) {
            for (observerName in observers) {
                val observer = objectObservableProvider.getObserver(observerName)
                observer?.onChange(objectObservableProvider.getName(), obj)
            }
        }
    }
}