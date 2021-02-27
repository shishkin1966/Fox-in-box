package ru.nextleap.sl.ui

import ru.nextleap.sl.ISubscriber
import ru.nextleap.sl.action.IActionHandler
import ru.nextleap.sl.action.IActionListener
import ru.nextleap.sl.lifecycle.ILifecycle
import ru.nextleap.sl.lifecycle.ILifecycleObservable


interface IFragment : ISubscriber, ILifecycle, IActionListener, IPermissionListener, IActionHandler,
    IViewGroup, ILifecycleObservable {

    fun stop()
}
