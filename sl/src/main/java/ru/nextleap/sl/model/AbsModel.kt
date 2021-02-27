package ru.nextleap.sl.model

import ru.nextleap.sl.lifecycle.ILifecycle
import ru.nextleap.sl.lifecycle.ILifecycleListener
import ru.nextleap.sl.lifecycle.LifecycleObserver

@Suppress("UNCHECKED_CAST")
abstract class AbsModel(private val modelView: IModelView) : IModel, ILifecycleListener {

    private val lifecycle = LifecycleObserver(this)

    init {
        modelView.addLifecycleObserver(this)
    }

    override fun <M : IModelView> getView(): M {
        return modelView as M
    }

    override fun isValid(): Boolean {
        return modelView.isValid()
    }

    override fun addLifecycleObserver(stateable: ILifecycle) {
        modelView.addLifecycleObserver(stateable)
    }

    override fun getState(): Int {
        return lifecycle.getState()
    }

    override fun setState(state: Int) {
        lifecycle.setState(state)
    }

    override fun onCreateView() = Unit

    override fun onReadyView() = Unit

    override fun onDestroyView() = Unit

}
