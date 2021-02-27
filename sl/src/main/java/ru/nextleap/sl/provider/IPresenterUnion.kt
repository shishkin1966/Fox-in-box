package ru.nextleap.sl.provider

import ru.nextleap.sl.ISmallUnion
import ru.nextleap.sl.presenter.IPresenter

interface IPresenterUnion : ISmallUnion<IPresenter> {
    /**
     * Получить presenter
     *
     * @param name имя презентера
     * @return презентер
     */
    fun <C : IPresenter> getPresenter(name: String): C?

}
