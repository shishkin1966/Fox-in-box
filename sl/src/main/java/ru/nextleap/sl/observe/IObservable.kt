package ru.nextleap.sl.observe

import ru.nextleap.sl.INamed
import ru.nextleap.sl.provider.IObservableSubscriber

interface IObservable : INamed {
    /**
     * Добавить слушателя к слушаемому объекту
     *
     * @param subscriber слушатель
     */
    fun addObserver(subscriber: IObservableSubscriber)

    /**
     * Удалить слушателя у слушаемого объекта
     *
     * @param subscriber слушатель
     */
    fun removeObserver(subscriber: IObservableSubscriber)

    /**
     * Зарегестрировать слушаемый объект. Вызывается при появлении
     * первого слушателя
     */
    fun register()

    /**
     * Отменить регистрацию слушаемого объекта. Вызывается при удалении
     * последнего слушателя
     */
    fun unregister()

    /**
     * Событие - в слушаемом объекте произошли изменения
     *
     * @param obj объект изменения
     */
    fun onChange(obj: Any)

    /**
     * Получить список слушателей
     *
     * @return список слушателей
     */
    fun getObservers(): List<IObservableSubscriber>

    /**
     * Получить слушателя
     *
     * @param name имя слушателя
     * @return слушатель
     */
    fun getObserver(name: String): IObservableSubscriber?

    /**
     * Остановить слушателя
     */
    fun stop()
}
