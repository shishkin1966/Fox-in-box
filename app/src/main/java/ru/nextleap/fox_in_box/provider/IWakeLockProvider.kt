package ru.nextleap.fox_in_box.provider

import ru.nextleap.sl.IProvider

interface IWakeLockProvider : IProvider {
    fun acquire()

    fun release()
}