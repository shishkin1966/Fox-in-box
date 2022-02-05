package ru.nextleap.common

import android.os.Handler
import android.os.HandlerThread




/**
 * Класс, устраняющий дребезг (частое повторение) события
 *
 * @param delay задержка, после которой запустится действие
 * @param skip  количество событий, которое будет пропущено перед запуском задержки
 */
@Suppress("UNCHECKED_CAST", "unused")
open class Debounce(delay: Long, skip: Int = 0) : Runnable {

    private var delay: Long = 5000 //5 sec
    private var skip = 0
    private var handler: Handler = ApplicationUtils.getHandler()

    init {
        if (!ApplicationUtils.isMainThread()) {
            val otherThread = HandlerThread("HandlerThread")
            handler = Handler(otherThread.looper)
        }

        this.delay = delay
        this.skip = skip
    }

    /**
     * Событие
     */
    fun onEvent() {
        if (skip >= 0) {
            skip--
        }

        if (skip < 0) {
            handler.removeCallbacks(this)
            handler.postDelayed(this, delay)
        }
    }

    override fun run() {}

    /**
     * остановить объект
     */
    fun finish() {
        handler.removeCallbacks(this)
    }

}

