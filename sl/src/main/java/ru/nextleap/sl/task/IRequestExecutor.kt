package ru.nextleap.sl.task

import ru.nextleap.sl.request.IRequest


interface IRequestExecutor : IExecutor {

    fun execute(request: IRequest)

    fun isShutdown(): Boolean

}
