package ru.nextleap.sl.action.handler

import ru.nextleap.sl.action.IAction
import ru.nextleap.sl.action.IActionHandler
import ru.nextleap.sl.message.IMessage
import ru.nextleap.sl.presenter.IPresenter

class PresenterActionHandler(private val presenter: IPresenter) : IActionHandler {

    override fun onAction(action: IAction): Boolean {
        if (!presenter.isValid()) return false

        if (action is IMessage) {
            action.read(presenter)
            return true
        }
        return false
    }

}
