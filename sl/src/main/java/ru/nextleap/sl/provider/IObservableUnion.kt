package ru.nextleap.sl.provider

import ru.nextleap.sl.ISmallUnion
import ru.nextleap.sl.observe.IObservable

interface IObservableUnion : ISmallUnion<IObservableSubscriber> {
    /**
     * Получить список слушаемых объектов
     *
     * @return список слушаемых(IObservable) объектов
     */
    fun getObservables(): List<IObservable>

    /**
     * Зарегестрировать слушаемый объект
     *
     * @param observable слушаемый объект
     */
    fun register(observable: IObservable): Boolean

    /**
     * Отменить регистрацию слушаемего объекта
     *
     * @param observable слушаемый объект
     */
    fun unregister(observable: IObservable): Boolean

    /**
     * Событие - изменился слушаемый объект
     *
     * @param name имя слушаемого объекта
     * @param obj новое значение
     */
    fun onChange(name: String, obj: Any)

    /**
     * Получить слушаемый объект
     *
     * @param name имя слушаемого объекта
     * @return слушаемый объект
     */
    fun getObservable(name: String): IObservable?
}
