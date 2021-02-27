package ru.nextleap.sl

/**
 * Интерфейс объединения подписчиков
 */
interface IUnion<T : IProviderSubscriber> : ISmallUnion<T> {

    /**
     * Установить текущего специалиста
     *
     * @param subscriber специалист
     */
    fun setCurrentSubscriber(subscriber: T): Boolean

    /**
     * Получить текущего специалиста
     *
     * @return специалист
     */
    fun getCurrentSubscriber(): T?
}
