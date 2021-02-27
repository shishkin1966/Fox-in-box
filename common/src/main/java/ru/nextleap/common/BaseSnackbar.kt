package ru.nextleap.common;

import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.CheckResult
import androidx.annotation.StringRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar

class BaseSnackbar {
    fun make(view: View, @StringRes titleRes: Int, duration: Int, type: Int): Snackbar {
        val context = view.context
        return make(view, context.getText(titleRes), duration, type)
    }

    fun make(
        view: View, title: CharSequence, duration: Int,
        type: Int
    ): Snackbar {
        val snackbar = Snackbar.make(view, title, duration)
        val snackbarView = snackbar.view
        if (FrameLayout::class.java.isInstance(view)) {
            val params = snackbarView.layoutParams as FrameLayout.LayoutParams
            params.width = FrameLayout.LayoutParams.MATCH_PARENT
            params.setMargins(0, 0, 0, 0)
            snackbarView.layoutParams = params
        } else if (CoordinatorLayout::class.java.isInstance(view)) {
            val params = snackbarView.layoutParams as CoordinatorLayout.LayoutParams
            params.width = CoordinatorLayout.LayoutParams.MATCH_PARENT
            params.setMargins(0, 0, 0, 0)
            snackbarView.layoutParams = params
        }
        val textView =
            snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        val actionView =
            snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_action)
        textView.setTextColor(ApplicationUtils.getColor(view.context, R.color.white))
        textView.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            ApplicationUtils.getDimensionPx(view.context, R.dimen.text_size_large)
        )
        actionView.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            ApplicationUtils.getDimensionPx(view.context, R.dimen.text_size_large)
        )
        if (ApplicationUtils.hasJellyBeanMR1()) {
            textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        }
        val backgroundColor: Int
        when (type) {
            ApplicationUtils.MESSAGE_TYPE_ERROR -> {
                snackbar.setActionTextColor(ApplicationUtils.getColor(view.context, R.color.gray))
                backgroundColor = R.color.red
            }

            ApplicationUtils.MESSAGE_TYPE_WARNING -> {
                snackbar.setActionTextColor(ApplicationUtils.getColor(view.context, R.color.gray))
                backgroundColor = R.color.orange
            }
            else -> {
                snackbar.setActionTextColor(ApplicationUtils.getColor(view.context, R.color.orange))
                backgroundColor = R.color.gray_dark
            }
        }
        snackbarView.setBackgroundColor(ApplicationUtils.getColor(view.context, backgroundColor))
        textView.isSingleLine = false
        textView.gravity = Gravity.CENTER_HORIZONTAL
        return snackbar
    }

    @CheckResult
    fun make(
        view: View, title: CharSequence, duration: Int,
        type: Int, action: String, listener: View.OnClickListener
    ): Snackbar {
        val snackbar = make(view, title, duration, type)
        snackbar.setAction(action, listener)
        val snackbarView = snackbar.view
        val textView =
            snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        if (ApplicationUtils.hasJellyBeanMR1()) {
            textView.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
        }
        return snackbar
    }

}
