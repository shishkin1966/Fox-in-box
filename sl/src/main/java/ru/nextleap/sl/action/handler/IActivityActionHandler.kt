package ru.nextleap.sl.action.handler

import android.view.View
import ru.nextleap.sl.action.ShowErrorAction
import ru.nextleap.sl.action.ShowKeyboardAction
import ru.nextleap.sl.action.ShowSnackbarAction

interface IActivityActionHandler {

    fun hideKeyboard()

    fun showSnackbar(action: ShowSnackbarAction)

    fun showKeyboard(action: ShowKeyboardAction)

    fun getRootView(): View

    fun hideSnackbar()

    fun grantPermission(permission: String)

    fun grantPermission(permission: String, listener: String?, helpMessage: String?)

    fun showProgressBar()

    fun hideProgressBar()

    fun showErrorAction(action: ShowErrorAction)
}