package ru.nextleap.fox_in_box.presenter

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.R
import ru.nextleap.fox_in_box.provider.Providers
import ru.nextleap.fox_in_box.screen.authorization.AuthorizationFragment
import ru.nextleap.fox_in_box.screen.home.HomeFragment
import ru.nextleap.sl.action.ShowSnackbarAction
import ru.nextleap.sl.presenter.AbsPresenter
import ru.nextleap.sl.provider.ApplicationProvider
import java.util.*


class OnBackPressedPresenter : AbsPresenter() {
    companion object {
        const val NAME = "OnBackPressedPresenter"
    }

    private var doubleBackPressedOnce = false
    private var timer: Timer? = null

    override fun getName(): String {
        return NAME
    }

    fun onClick(): Boolean {
        val fragment =
            ApplicationSingleton.instance.activityProvider.getCurrentFragment<Fragment>()
        var isShow = false
        if (fragment == null) {
            isShow = true
        } else {
            if (fragment is HomeFragment) {
                isShow = true
            }
            if (!isShow && fragment is AuthorizationFragment) {
                isShow = true
            }
        }
        if (!isShow) {
            return false
        }

        if (isValid()) {
            if (!doubleBackPressedOnce) {
                doubleBackPressedOnce = true
                ApplicationSingleton.instance.activityProvider.getCurrentSubscriber()?.addAction(
                    ShowSnackbarAction(ApplicationProvider.appContext.getString(R.string.double_back_pressed)).setDuration(
                        Snackbar.LENGTH_SHORT
                    ).setAction(ApplicationProvider.appContext.getString(R.string.exit))
                )
                startTimer()
                return true
            } else {
                Providers.exitApplication()
                return true
            }
        }
        return false
    }

    private fun startTimer() {
        if (timer != null) {
            stopTimer()
        }
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                doubleBackPressedOnce = false
            }
        }, 3000)
    }

    private fun stopTimer() {
        if (timer != null) {
            timer?.cancel()
            timer = null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        stopTimer()
    }

    override fun isRegister(): Boolean {
        return false
    }
}
