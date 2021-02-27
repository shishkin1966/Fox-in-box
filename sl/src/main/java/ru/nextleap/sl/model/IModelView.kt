package ru.nextleap.sl.model

import ru.nextleap.sl.IValidated
import ru.nextleap.sl.lifecycle.ILifecycleObservable
import ru.nextleap.sl.ui.IViewGroup

interface IModelView : IValidated, IViewGroup, ILifecycleObservable {

    /**
     * Закрыть ModelView объект
     */
    fun stop()

    /**
     * Получить модель
     *
     * @return модель
     */
    fun <M : IModel> getModel(): M

    /**
     * Установить модель
     *
     * @param model модель
     */
    fun <M : IModel> setModel(model: M)
}
