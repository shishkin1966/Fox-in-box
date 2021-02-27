package ru.nextleap.sl.presenter

import ru.nextleap.sl.model.IModel
import ru.nextleap.sl.model.IModelView


@Suppress("UNCHECKED_CAST")
abstract class AbsModelPresenter() : AbsPresenter(), IModelPresenter {
    private lateinit var model: IModel

    constructor(model: IModel) : this() {
        this.model = model
    }

    override fun <M : IModel> getModel(): M {
        return model as M
    }

    override fun <C : IModelView> getView(): C {
        return model.getView()
    }
}
