package ru.nextleap.sl.provider

import ru.nextleap.sl.IProvider
import ru.nextleap.sl.request.IRequest

interface IRequestProvider : IProvider {
    /**
     * Выполнить запрос
     *
     * @param request запрос
     */
    fun request(request: IRequest)
}
