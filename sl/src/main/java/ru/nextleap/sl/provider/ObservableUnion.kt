package ru.nextleap.sl.provider

import ru.nextleap.sl.AbsSmallUnion
import ru.nextleap.sl.IProvider
import ru.nextleap.sl.Secretary
import ru.nextleap.sl.observe.IObservable

class ObservableUnion : AbsSmallUnion<IObservableSubscriber>(),
    IObservableUnion {
    private val secretary = Secretary<IObservable>()

    companion object {
        const val NAME = "ObservableUnion"
    }

    override fun getName(): String {
        return NAME
    }

    override operator fun compareTo(other: IProvider): Int {
        return if (other is IObservableUnion) 0 else 1
    }

    override fun getObservables(): List<IObservable> {
        return secretary.values()
    }

    override fun register(subscriber: IObservableSubscriber): Boolean {
        super.register(subscriber)

        val list = subscriber.getObservable()
        for (observable in getObservables()) {
            val name = observable.getName()
            if (list.contains(name)) {
                observable.addObserver(subscriber)
            }
        }
        return true
    }

    override fun unregister(subscriber: IObservableSubscriber) {
        super.unregister(subscriber)

        val list = subscriber.getObservable()
        for (observable in getObservables()) {
            if (list.contains(observable.getName())) {
                observable.removeObserver(subscriber)
            }
        }
    }

    override fun onUnRegister() {
        for (observable in getObservables()) {
            observable.unregister()
        }
    }

    override fun onChange(name: String, obj: Any) {
        val observable = getObservable(name)
        observable?.onChange(obj)
    }

    override fun getObservable(name: String): IObservable? {
        return secretary.get(name)
    }

    override fun register(observable: IObservable): Boolean {
        secretary.put(observable.getName(), observable)
        return true
    }

    override fun unregister(observable: IObservable): Boolean {
        if (secretary.containsKey(observable.getName()) && observable == secretary.get(observable.getName())) {
            secretary.remove(observable.getName())
            return true
        }
        return false
    }

    override fun stop() {
        super.stop()

        for (observable in getObservables()) {
            observable.stop()
        }
    }

}
