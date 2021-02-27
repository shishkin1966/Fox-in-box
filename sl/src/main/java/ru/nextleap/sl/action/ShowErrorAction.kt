package ru.nextleap.sl.action

import android.widget.Toast
import ru.nextleap.common.ApplicationUtils


class ShowErrorAction(private val message: String?) : AbsAction(), IAction {

    private var duration = Toast.LENGTH_LONG
    private var type = ApplicationUtils.MESSAGE_TYPE_ERROR

    fun getMessage(): String? {
        return message
    }

    fun getDuration(): Int {
        return duration
    }

    fun getType(): Int {
        return type
    }

}
