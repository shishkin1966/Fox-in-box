package ru.nextleap.sl.action

import com.google.android.material.snackbar.Snackbar
import ru.nextleap.common.ApplicationUtils


class ShowSnackbarAction(private val message: String) : AbsAction(), IAction {

    private var action: String? = null
    private var duration = Snackbar.LENGTH_SHORT
    private var type = ApplicationUtils.MESSAGE_TYPE_INFO

    fun getMessage(): String {
        return message
    }

    fun getAction(): String? {
        return action
    }

    fun setAction(action: String): ShowSnackbarAction {
        this.action = action
        return this
    }

    fun getDuration(): Int {
        return duration
    }

    fun setDuration(duration: Int): ShowSnackbarAction {
        this.duration = duration
        return this
    }

    fun getType(): Int {
        return type
    }

    fun setType(type: Int): ShowSnackbarAction {
        this.type = type
        return this
    }

}
