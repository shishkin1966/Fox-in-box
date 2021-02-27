package ru.nextleap.sl.lifecycle

interface ILifecycleObservable : ILifecycle {
    fun addLifecycleObserver(stateable: ILifecycle)

    fun removeLifecycleObserver(stateable: ILifecycle)

    fun clearLifecycleObservers()
}