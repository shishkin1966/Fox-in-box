package ru.nextleap.sl.message

import ru.nextleap.sl.IValidated
import ru.nextleap.sl.action.DialogResultAction

interface IDialogResultListener : IValidated {
    fun onDialogResult(action: DialogResultAction)
}
