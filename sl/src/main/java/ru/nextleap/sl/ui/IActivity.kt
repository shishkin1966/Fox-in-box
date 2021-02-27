package ru.nextleap.sl.ui

import ru.nextleap.sl.IProviderSubscriber
import ru.nextleap.sl.action.IActionHandler
import ru.nextleap.sl.action.IActionListener
import ru.nextleap.sl.lifecycle.ILifecycle
import ru.nextleap.sl.lifecycle.ILifecycleObservable

interface IActivity : IProviderSubscriber, ILifecycle, IActionListener, IActionHandler,
    ILifecycleObservable, IViewGroup, IPermissionListener {

    fun lockOrientation(orientation: Int)

}
