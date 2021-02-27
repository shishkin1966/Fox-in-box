package ru.nextleap.sl

import android.os.*
import android.util.Log
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock

/**
 * Класс помещает действие, которое будет выполняться в другом потоке, чем ваш собственный.
 * Очередь сообщений будут автоматически прекращаться после определенного периода времени.
 */
@Suppress("UNCHECKED_CAST")
class AutoCompleteHandler<E>(private val name: String) {
    /**
     * Callback interface you can use to handle posted events.
     */
    interface OnHandleEventListener<E> {
        fun onHandleEvent(event: E)
    }

    /**
     * Callback interface to manage message queue stopping.
     */
    interface OnShutdownListener {
        fun onShutdown(handler: AutoCompleteHandler<*>)
    }

    companion object {
        private val NAME = AutoCompleteHandler::class.java.name
        private const val EVENT_TOKEN = -0x2f2f0ff3
        private const val SHUTDOWN_TOKEN = -0x21524111
    }

    private var shutdownTimeout = TimeUnit.SECONDS.toMillis(30)
    private var onHandleEventListener: OnHandleEventListener<E>? = null
    private var onShutdownListener: OnShutdownListener? = null
    private var handler: AsyncHandler? = null
    private val lock: ReentrantLock = ReentrantLock()

    /**
     * Set shutdown timeout in milliseconds when messages queue will be stopped
     * after queue is empty.
     *
     * @param shutdownTimeout The timeout in milliseconds.
     */
    fun setShutdownTimeout(shutdownTimeout: Long) {
        lock.lock()
        this.shutdownTimeout = try {
            shutdownTimeout
        } finally {
            lock.unlock()
        }
    }

    /**
     * Register callback to manage messages queue stopping.
     */
    fun setOnShutdownListener(onShutdownListener: OnShutdownListener?) {
        lock.lock()
        this.onShutdownListener = try {
            onShutdownListener
        } finally {
            lock.unlock()
        }
    }

    /**
     * Register callback to manage events.
     */
    fun setOnHandleEventListener(onHandleEventListener: OnHandleEventListener<E>?) {
        lock.lock()
        this.onHandleEventListener = try {
            onHandleEventListener
        } finally {
            lock.unlock()
        }
    }

    @JvmOverloads
    fun post(event: E, cancelPrevious: Boolean = false) {
        lock.lock()
        try {
            if (handler == null) {
                val thread =
                    HandlerThread(name, Process.THREAD_PRIORITY_BACKGROUND)
                thread.priority = Thread.MAX_PRIORITY
                thread.start()
                handler = AsyncHandler(thread.looper)
            }
            handler!!.removeMessages(SHUTDOWN_TOKEN)
            if (cancelPrevious) {
                handler!!.removeMessages(EVENT_TOKEN)
            }
            handler!!.obtainMessage(EVENT_TOKEN, event).sendToTarget()
        } finally {
            lock.unlock()
        }
    }

    private fun handleEvent(event: E) {
        onHandleEventListener?.onHandleEvent(event)
    }

    private fun scheduleShutdown() {
        lock.lock()
        try {
            if (handler != null) {
                val shutdownMsg =
                    handler!!.obtainMessage(SHUTDOWN_TOKEN)
                handler!!.sendMessageDelayed(shutdownMsg, shutdownTimeout)
            }
        } finally {
            lock.unlock()
        }
    }

    fun shutdown() {
        lock.lock()
        try {
            if (handler != null) {
                handler!!.looper.quit()
                handler = null
                if (onShutdownListener != null) {
                    onShutdownListener!!.onShutdown(this)
                }
            }
        } finally {
            lock.unlock()
        }
    }

    private inner class AsyncHandler(looper: Looper?) : Handler(looper!!) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                EVENT_TOKEN -> {
                    val event = msg.obj as E
                    try {
                        handleEvent(event)
                    } catch (e: Exception) {
                        Log.e(NAME, e.message!!)
                    }
                    scheduleShutdown()
                }
                SHUTDOWN_TOKEN -> shutdown()
                else -> super.handleMessage(msg)
            }
        }
    }


}