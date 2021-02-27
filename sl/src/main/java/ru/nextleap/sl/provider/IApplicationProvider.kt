package ru.nextleap.sl.provider

import ru.nextleap.sl.IProvider

interface IApplicationProvider : IProvider {
    /**
     * Флаг - выход из приложения
     *
     * @return true = приложение остановлено
     */
    fun isExit(): Boolean

    fun setStart()
}
