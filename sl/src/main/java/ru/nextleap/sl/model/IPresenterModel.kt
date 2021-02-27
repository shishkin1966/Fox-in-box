package ru.nextleap.sl.model

import ru.nextleap.sl.presenter.IModelPresenter

interface IPresenterModel : IModel {
    /**
     * Установить презентер модели
     *
     * @param presenter презентер
     */
    fun setPresenter(presenter: IModelPresenter)

    /**
     * Получить презентер модели
     *
     * @return презентер
     */
    fun <C> getPresenter(): C

}
