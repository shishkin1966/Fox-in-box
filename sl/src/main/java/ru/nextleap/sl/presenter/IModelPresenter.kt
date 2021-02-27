package ru.nextleap.sl.presenter

import ru.nextleap.sl.model.IModel
import ru.nextleap.sl.model.IModelView

interface IModelPresenter : IPresenter {
    /**
     * Получить модель презентера
     *
     * @return the model
     */
    fun <M : IModel> getModel(): M

    /**
     * Получить View модели
     *
     * @return the view model
     */
    fun <V : IModelView> getView(): V

}
