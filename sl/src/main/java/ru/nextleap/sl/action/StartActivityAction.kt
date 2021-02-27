package ru.nextleap.sl.action

import android.content.Intent

class StartActivityAction(private val intent: Intent) : AbsAction(), IAction {

    fun getIntent(): Intent {
        return intent
    }

}
