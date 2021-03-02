package ru.nextleap.sl.action.handler

import android.app.Activity
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.nextleap.common.ApplicationUtils
import ru.nextleap.common.KeyboardRunnable
import ru.nextleap.sl.IValidated
import ru.nextleap.sl.R
import ru.nextleap.sl.action.*
import ru.nextleap.sl.provider.ApplicationProvider
import ru.nextleap.sl.provider.ErrorProvider
import ru.nextleap.sl.provider.IErrorProvider
import ru.nextleap.sl.ui.MaterialDialogExt


class FragmentActionHandler(private val fragment: Fragment) : BaseActionHandler(), IFragmentActionHandler {
    override fun onAction(action: IAction): Boolean {
        if (fragment is IValidated && !fragment.isValid()) return false

        if (super.onAction(action)) return true

        if (action is HideProgressBarAction) {
            hideProgressBar()
            return true
        }
        if (action is ShowProgressBarAction) {
            showProgressBar()
            return true
        }
        if (action is ShowKeyboardAction) {
            showKeyboard(action)
            return true
        }
        if (action is HideKeyboardAction) {
            hideKeyboard()
            return true
        }
        if (action is StopAction) {
            if (fragment.activity != null) {
                val activity = fragment.activity as AppCompatActivity
                activity.onBackPressed()
            }
            return true
        }
        if (action is ShowListDialogAction) {
            showListDialog(action)
            return true
        }
        if (action is ShowDialogAction) {
            showDialog(action)
            return true
        }

        if (action is ShowErrorAction) {
            showErrorAction(action)
            return true
        }

        return false
    }

    override fun showErrorAction(action: ShowErrorAction) {
        val view = fragment.view?.findViewById<View>(R.id.errorMessage)
        if (view != null && view is TextView) {
            view.clearAnimation()
            view.visibility = View.VISIBLE
            view.setOnClickListener(View.OnClickListener {
                ApplicationUtils.collapse(view)
                view.text = null
            })
            view.text = action.getMessage()
            ApplicationUtils.expand(view)
        } else {
            val provider =
                ApplicationProvider.serviceLocator?.get<IErrorProvider>(ErrorProvider.NAME)
            provider?.onError("", action.getMessage(), true)
        }
    }

    override fun showProgressBar() {
        val view = fragment.view?.findViewById<View>(R.id.progressBar)
        view?.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        val view = fragment.view?.findViewById<View>(R.id.progressBar)
        view?.visibility = View.INVISIBLE
    }

    override fun showKeyboard(action: ShowKeyboardAction) {
        val activity = fragment.activity ?: return

        KeyboardRunnable(activity, action.getView()).run()
    }

    override fun hideKeyboard() {
        val activity = fragment.activity ?: return
        if (activity.isFinishing) return

        val imm = ApplicationUtils.getSystemService<InputMethodManager>(
            activity,
            Activity.INPUT_METHOD_SERVICE
        )
        var view = activity.currentFocus
        if (view == null) {
            view = getRootView()
        }
        if (view != null) {
            activity.window
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun getRootView(): View? {
        val view = fragment.view?.findViewById<View>(R.id.root)
        return view ?: fragment.view
    }

    override fun showDialog(action: ShowDialogAction) {
        val activity = fragment.activity ?: return
        if (activity.isFinishing) return

        MaterialDialogExt(
            activity,
            action.getListener(),
            action.getId(),
            action.getTitle(),
            action.getMessage(),
            action.getButtonPositive(),
            action.getButtonNegative(),
            action.isCancelable()
        ).show()
    }

    override fun showListDialog(action: ShowListDialogAction) {
        val activity = fragment.activity ?: return
        if (activity.isFinishing) return

        if (action.getList() == null) return

        MaterialDialogExt(
            activity,
            action.getListener(),
            action.getId(),
            action.getTitle(),
            action.getMessage(),
            action.getList()!!,
            action.getSelected(),
            action.isMultiSelect(),
            action.getButtonPositive(),
            action.getButtonNegative(),
            action.isCancelable()
        ).show()
    }
}
