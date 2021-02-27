package ru.nextleap.sl.action

import android.content.Intent

class StartActivityForResultAction(private val intent: Intent, private val requestCode: Int) :
    AbsAction() {

    fun getIntent(): Intent {
        return intent
    }

    fun getRequestCode(): Int {
        return requestCode
    }

}
