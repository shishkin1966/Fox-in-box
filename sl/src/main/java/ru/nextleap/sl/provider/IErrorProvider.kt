package ru.nextleap.sl.provider

import ru.nextleap.sl.IProvider
import ru.nextleap.sl.data.ExtError


/**
 * Интерфейс провайдера обработки ошибок
 */
interface IErrorProvider : IProvider {
    /**
     * Событие - ошибка
     *
     * @param source источник ошибки
     * @param e      Exception
     */
    fun onError(source: String, e: Exception)

    /**
     * Событие - ошибка
     *
     * @param source    источник ошибки
     * @param throwable Throwable
     */
    fun onError(source: String, throwable: Throwable)

    /**
     * Событие - ошибка
     *
     * @param source         источник ошибки
     * @param e              Exception
     * @param displayMessage текст ошибки пользователю
     */
    fun onError(source: String, e: Exception, displayMessage: String?)

    /**
     * Событие - ошибка
     *
     * @param source    источник ошибки
     * @param message   текст ошибки пользователю
     * @param isDisplay true - отображать на сообщение на дисплее, false - сохранять в журнале
     */
    fun onError(source: String, message: String?, isDisplay: Boolean)

    /**
     * Событие - ошибка
     *
     * @param extError ошибка
     */
    fun onError(error: ExtError)

}
