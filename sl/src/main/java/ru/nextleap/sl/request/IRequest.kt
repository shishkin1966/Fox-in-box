package ru.nextleap.sl.request

import ru.nextleap.sl.INamed

interface IRequest : Runnable, INamed, Comparable<IRequest> {

    /**
     * Получить id запроса
     *
     * @return id запроса
     */
    fun getId(): Int

    /**
     * Установить id запроса
     *
     * @param id запроса
     * @return запрос
     */
    fun setId(id: Int): IRequest

    /**
     * Получить ранг запроса
     *
     * @return ранг запроса
     */
    fun getRank(): Int

    /**
     * Установить ранг запроса
     *
     * @param rank ранг запроса
     * @return запрос
     */
    fun setRank(rank: Int): IRequest

    /**
     * Установить флаг - запрос прерван
     */
    fun setCanceled()

    /**
     * Проверить прерван ли запрос
     *
     * @return true - запрос прерван
     */
    fun isCancelled(): Boolean

    /**
     * Проверить работоспособность запроса
     *
     * @return true - запрос может обеспечивать свою функциональность
     */
    fun isValid(): Boolean

    /**
     * Проверить является ли запрос уникальным
     *
     * @return true - при запуске все предыдущие такие же запросы будут прерваны
     */
    fun isDistinct(): Boolean

    /**
     * Что делать если такой запрос уже есть
     *
     * @return 0 - удалить старые запросы, 1 = не выполнять
     */
    fun getAction(oldRequest: IRequest): Int

}
