package ru.nextleap.sl.lifecycle

import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.ArrayList

class LifecycleObservable(state: Int) : ILifecycleObservable {
    private val list = Collections.synchronizedList(ArrayList<WeakReference<ILifecycle>>())
    private var state = Lifecycle.VIEW_CREATE

    init {
        setState(state)
    }

    override fun setState(state: Int) {
        this.state = state
        for (stateable in list) {
            if (stateable?.get() != null) {
                stateable.get()!!.setState(this.state)
            }
        }
    }

    override fun getState(): Int {
        return state
    }

    /**
     * Добавить слушателя состояний
     *
     * @param stateable слушатель состояний
     */
    override fun addLifecycleObserver(stateable: ILifecycle) {
        for (ref in list) {
            if (ref?.get() != null && ref.get() === stateable) {
                return
            }
        }

        stateable.setState(state)
        list.add(WeakReference(stateable))
    }

    /**
     * Удалить слушателя состояний
     *
     * @param stateable слушатель состояний
     */
    override fun removeLifecycleObserver(stateable: ILifecycle) {
        for (ref in list) {
            if (ref?.get() != null && ref.get() === stateable) {
                list.remove(ref)
                return
            }
        }
    }

    /**
     * Удалить всех слушателей
     */
    override fun clearLifecycleObservers() {
        list.clear()
    }


}
