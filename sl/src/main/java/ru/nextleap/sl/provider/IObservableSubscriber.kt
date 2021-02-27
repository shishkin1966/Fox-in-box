package ru.nextleap.sl.provider

import ru.nextleap.sl.IProviderSubscriber
import ru.nextleap.sl.IValidated
import ru.nextleap.sl.lifecycle.ILifecycle

interface IObservableSubscriber : ILifecycle, IProviderSubscriber, IValidated {
    /**
     * Получить список имен слушаемых объектов
     *
     * @return список имен слушаемых объектов
     */
    fun getObservable(): List<String>

    /**
     * Событие - объект изменен
     *
     * @param name имя слушаемого объекта
     * @param obj значение слушаемого объекта
     */
    fun onChange(name: String, obj: Any)

}
