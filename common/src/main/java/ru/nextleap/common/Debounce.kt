package ru.nextleap.common;

import android.os.Handler

/**
 * Класс, устраняющий дребезг (частое повторение) события
 *
 * @param delay задержка, после которой запустится действие
 * @param skip  количество событий, которое будет пропущено перед запуском задержки
 */
open class Debounce(delay: Long, skip: Int = 0) : Runnable {

    private var delay: Long = 5000 //5 sec
    private var skip = 0
    private val handler: Handler = Handler()

    init {
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

