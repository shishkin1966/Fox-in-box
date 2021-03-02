package ru.nextleap.sl.action.handler

import android.view.View
import ru.nextleap.sl.action.ShowDialogAction
import ru.nextleap.sl.action.ShowErrorAction
import ru.nextleap.sl.action.ShowKeyboardAction
import ru.nextleap.sl.action.ShowListDialogAction

interface IFragmentActionHandler {
    fun showErrorAction(action: ShowErrorAction)

    fun showProgressBar()

    fun hideProgressBar()

    fun showKeyboard(action: ShowKeyboardAction)

    fun hideKeyboard()

    fun getRootView(): View?

    fun showDialog(action: ShowDialogAction)

    fun showListDialog(action: ShowListDialogAction)
}