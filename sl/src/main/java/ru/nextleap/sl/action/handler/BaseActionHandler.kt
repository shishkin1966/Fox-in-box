package ru.nextleap.sl.action.handler

import ru.nextleap.common.ApplicationUtils
import ru.nextleap.sl.action.IAction
import ru.nextleap.sl.action.IActionHandler
import ru.nextleap.sl.action.ShowMessageAction
import ru.nextleap.sl.provider.ApplicationProvider

open class BaseActionHandler : IActionHandler {
    override fun onAction(action: IAction): Boolean {
        if (action is ShowMessageAction) {
            showMessage(action)
            return true
        }

        return false
    }

    private fun showMessage(action: ShowMessageAction) {
        if (action.getMessage().isNullOrEmpty()) return

        ApplicationUtils.showToast(
            ApplicationProvider.appContext,
            action.getMessage(),
            action.getDuration(),
            action.getType()
        )
    }
}
