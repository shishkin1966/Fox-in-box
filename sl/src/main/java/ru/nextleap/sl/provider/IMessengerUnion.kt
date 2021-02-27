package ru.nextleap.sl.provider

import ru.nextleap.sl.ISmallUnion
import ru.nextleap.sl.message.IMessage

/**
 * Интерфейс объединения, предоставляющего Messager сервис подписчикам
 */
interface IMessengerUnion : ISmallUnion<IMessengerSubscriber> {
    /**
     * Получить сообщения подписчика
     *
     * @param subscriber подписчик
     * @return список сообщений
     */
    fun getMessages(subscriber: IMessengerSubscriber): List<IMessage>

    /**
     * Добавить почтовое сообщение
     *
     * @param message сообщение
     */
    fun addMessage(message: IMessage)

    /**
     * Добавить почтовое сообщение, только в случае если провайдер присутствует
     *
     * @param message the message
     */
    fun addNotMandatoryMessage(message: IMessage)

    /**
     * Заменить почтовое сообщение
     *
     * @param message the message
     */
    fun replaceMessage(message: IMessage)

    /**
     * Удалить почтовое сообщение
     *
     * @param message the message
     */
    fun removeMessage(message: IMessage)

    /**
     * Удалить все сообщения
     */
    fun clearMessages()

    /**
     * Удалить сообщения подписчика
     *
     * @param subscriber имя подписчика
     */
    fun clearMessages(subscriber: String)

    /**
     * Читать почту подписчика
     *
     * @param subscriber почтовый подписчик
     */
    fun readMessages(subscriber: IMessengerSubscriber)

    /**
     * Добавить список рассылки
     *
     * @param name      имя списка рассылки
     * @param addresses список рассылки
     */
    fun addMessagingList(name: String, addresses: List<String>)

    /**
     * Добавить список рассылки
     *
     * @param name      имя списка рассылки
     * @param addresses список рассылки
     */
    fun addMessagingList(name: String, addresses: Array<String>)

    /**
     * Удалить список рассылки
     *
     * @param name имя списка рассылки
     */
    fun removeMessagingList(name: String)

    /**
     * Получить список рассылки
     *
     * @return список рассылки
     */
    fun getMessagingList(name: String): List<String>?
}
