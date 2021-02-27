package ru.nextleap.common;

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager


class KeyboardRunnable(private val activity: Activity, private val view: View) : Runnable {

    private var mode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
    private val handler = Handler()

    constructor (activity: Activity, view: View, mode: Int) : this(activity, view) {
        this.mode = mode
    }

    private fun post() {
        handler.postDelayed(this, 100)
    }

    override fun run() {
        if (activity.isFinishing()) {
            return
        }

        activity.getWindow().setSoftInputMode(mode)
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (!(view.isFocusable() && view.isFocusableInTouchMode())) {
            return
        } else if (!view.requestFocus()) {
            post()
        } else if (!imm.isActive(view)) {
            post()
        } else if (!imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)) {
            post()
        }
    }
}
