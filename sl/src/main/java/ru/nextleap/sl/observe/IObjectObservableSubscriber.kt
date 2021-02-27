package ru.nextleap.sl.observe

import ru.nextleap.sl.provider.IObservableSubscriber

interface IObjectObservableSubscriber : IObservableSubscriber {
    /**
     * Получить список слушаемых объектов БД
     *
     * @return список слушаемых объектов БД
     */
    fun getListenObjects(): List<String>
}
