package ru.nextleap.sl.action

import android.content.Intent

class StartChooseActivityAction(private val intent: Intent, private var title: String) :
    AbsAction() {

    fun getIntent(): Intent {
        return intent
    }

    fun getTitle(): String {
        return title
    }
}
