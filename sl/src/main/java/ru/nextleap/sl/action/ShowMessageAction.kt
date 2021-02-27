package ru.nextleap.sl.action

import android.widget.Toast
import ru.nextleap.common.ApplicationUtils


class ShowMessageAction(private val message: String?) : AbsAction(), IAction {

    private var title: String? = null
    private var duration = Toast.LENGTH_SHORT
    private var type = ApplicationUtils.MESSAGE_TYPE_INFO

    constructor(message: String, type: Int) : this(message) {
        this.type = type
    }

    constructor(title: String, message: String, type: Int) : this(message, type) {
        this.title = title
    }

    fun getMessage(): String? {
        return message
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String): ShowMessageAction {
        this.title = title
        return this
    }

    fun getDuration(): Int {
        return duration
    }

    fun setDuration(duration: Int): ShowMessageAction {
        this.duration = duration
        return this
    }

    fun getType(): Int {
        return type
    }

    fun setType(type: Int): ShowMessageAction {
        this.type = type
        return this
    }
}
