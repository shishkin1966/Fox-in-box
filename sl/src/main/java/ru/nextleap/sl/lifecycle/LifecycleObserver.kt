package ru.nextleap.sl.lifecycle

import java.lang.ref.WeakReference

class LifecycleObserver(listener: ILifecycleListener?) : ILifecycle {
    private var state = Lifecycle.VIEW_CREATE
    private var listener: WeakReference<ILifecycleListener>? = null

    init {
        if (listener != null) {
            this.listener = WeakReference(listener)
        }
        setState(Lifecycle.VIEW_CREATE)
    }

    /**
     * Получить состояние объекта
     *
     * @return состояние объекта
     */
    override fun getState(): Int {
        return state
    }

    /**
     * Установить состояние объекта
     *
     * @param state состояние объекта
     */
    override fun setState(state: Int) {
        this.state = state
        when (state) {
            Lifecycle.VIEW_CREATE -> {
                listener!!.get()!!.onCreateView()
            }

            Lifecycle.VIEW_READY -> {
                listener!!.get()!!.onReadyView()
            }

            Lifecycle.VIEW_DESTROY -> {
                listener!!.get()!!.onDestroyView()
            }
        }
    }
}
