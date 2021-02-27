package ru.nextleap.sl.request

import ru.nextleap.sl.ISubscriber
import ru.nextleap.sl.data.ExtResult

interface IResponseListener : ISubscriber {

    /**
     * Событие - пришел ответ с результатами запроса
     *
     * @param result - результат
     */
    fun response(result: ExtResult)

}
