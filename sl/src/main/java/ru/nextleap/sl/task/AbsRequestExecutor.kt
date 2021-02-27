package ru.nextleap.sl.task

import ru.nextleap.sl.IProvider
import ru.nextleap.sl.request.IRequest
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

abstract class AbsRequestExecutor : IRequestExecutor, IProvider, ExecutorService {

    abstract fun getExecutor(): RequestThreadPoolExecutor

    override fun execute(request: IRequest) {
        getExecutor().addRequest(request)
    }

    override fun stop() {
        getExecutor().stop()
    }

    override fun clear() {
        getExecutor().clear()
    }

    override fun cancelRequests(listener: String) {
        getExecutor().cancelRequests(listener)
    }

    override fun cancelRequests(listener: String, taskName: String) {
        getExecutor().cancelRequests(listener, taskName)
    }

    override fun isShutdown(): Boolean {
        return getExecutor().isShutdown
    }

    override fun execute(command: Runnable) {
        if (command is IRequest) {
            execute(command)
        }
    }

    override fun onUnRegister() = Unit

    override fun onRegister() = Unit

    override fun isValid(): Boolean {
        return !getExecutor().isShutdown
    }

    override fun isPersistent(): Boolean {
        return false
    }

    override operator fun compareTo(other: IProvider): Int {
        return if (other is IExecutor) 0 else 1
    }

    override fun shutdown() {
        getExecutor().shutdown()
    }

    override fun <T : Any?> submit(task: Callable<T>?): Future<T> {
        return getExecutor().submit(task)
    }

    override fun <T : Any?> submit(task: Runnable?, result: T): Future<T> {
        return getExecutor().submit(task, result)
    }

    override fun submit(task: Runnable?): Future<*> {
        return getExecutor().submit(task)
    }

    override fun shutdownNow(): MutableList<Runnable> {
        return getExecutor().shutdownNow()
    }

    override fun awaitTermination(timeout: Long, unit: TimeUnit?): Boolean {
        return getExecutor().awaitTermination(timeout, unit)
    }

    override fun <T : Any?> invokeAny(tasks: MutableCollection<out Callable<T>>?): T {
        return getExecutor().invokeAny(tasks)
    }

    override fun <T : Any?> invokeAny(
        tasks: MutableCollection<out Callable<T>>?,
        timeout: Long,
        unit: TimeUnit?
    ): T {
        return getExecutor().invokeAny(tasks, timeout, unit)
    }

    override fun isTerminated(): Boolean {
        return getExecutor().isTerminated
    }

    override fun <T : Any?> invokeAll(tasks: MutableCollection<out Callable<T>>?): MutableList<Future<T>> {
        return getExecutor().invokeAll(tasks)
    }

    override fun <T : Any?> invokeAll(
        tasks: MutableCollection<out Callable<T>>?,
        timeout: Long,
        unit: TimeUnit?
    ): MutableList<Future<T>> {
        return getExecutor().invokeAll(tasks, timeout, unit)
    }

}
