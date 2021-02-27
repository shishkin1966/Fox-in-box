package ru.nextleap.sl.task

import java.util.concurrent.Executor

interface IExecutor : Executor {
    fun cancelRequests(listener: String)

    fun cancelRequests(listener: String, taskName: String)

    fun stop()

    fun clear()

}
