package ru.nextleap.sl.action

import android.widget.EditText

class ShowKeyboardAction(private val view: EditText) : AbsAction(), IAction {

    fun getView(): EditText {
        return view
    }

}
