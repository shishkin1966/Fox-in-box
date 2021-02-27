package ru.nextleap.sl.presenter

import ru.nextleap.sl.action.IActionHandler
import ru.nextleap.sl.action.IActionListener
import ru.nextleap.sl.lifecycle.ILifecycleListener
import ru.nextleap.sl.provider.IMessengerSubscriber

interface IPresenter : ILifecycleListener, IActionListener, IActionHandler,
    IMessengerSubscriber {
    /**
     * Флаг - регистрировать презентер в объединении презентеров
     *
     * @return true - регистрировать (презентер - глобальный)
     */
    fun isRegister(): Boolean
}
