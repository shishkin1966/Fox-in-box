package ru.nextleap.sl.model

import ru.nextleap.sl.presenter.IModelPresenter


@Suppress("UNCHECKED_CAST")
abstract class AbsPresenterModel(modelView: IModelView) : AbsModel(modelView), IPresenterModel {
    private lateinit var presenter: IModelPresenter

    override fun setPresenter(presenter: IModelPresenter) {
        this.presenter = presenter
        super.addLifecycleObserver(this.presenter)
    }

    override fun <C> getPresenter(): C {
        return presenter as C
    }
}
