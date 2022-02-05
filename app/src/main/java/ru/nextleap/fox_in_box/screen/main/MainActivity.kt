package ru.nextleap.fox_in_box.screen.main

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.work.Data
import ru.nextleap.common.ApplicationUtils
import ru.nextleap.fox_in_box.ApplicationConstant.Companion.REQUEST_APPLICATION_UPDATE
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.GlideApp
import ru.nextleap.fox_in_box.R
import ru.nextleap.fox_in_box.presenter.OnBackPressedPresenter
import ru.nextleap.sl.action.*
import ru.nextleap.sl.provider.ApplicationProvider
import ru.nextleap.sl.ui.AbsContentActivity


class MainActivity : AbsContentActivity() {
    private val onBackPressedPresenter = OnBackPressedPresenter()
    lateinit var router: MainRouter

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (action is SnackBarAction) {
            onBackPressedPresenter.onClick()
            return true
        }

        if (super.onAction(action)) return true

        return false
    }

    override fun createModel(): MainModel {
        return MainModel(this)
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)

        setContentView(
            ApplicationSingleton.instance.desktopProvider.getLayoutId(
                "activity_main",
                R.layout.activity_main
            )
        )

        router = MainRouter(this)

        requestedOrientation =
            if (ApplicationUtils.is10inchTablet(ApplicationProvider.appContext)) {
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            } else {
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        lockOrientation(requestedOrientation)

        onNewIntent(intent)
    }

    override fun onResume() {
        super.onResume()

        if (intent != null) {
            getModel<MainModel>().getPresenter<MainPresenter>()
                .addAction(DataAction(MainPresenter.IntentAction, intent))
        }
    }

    override fun getContentResId(): Int {
        return R.id.content
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        this.intent = intent
    }

    override fun onStart() {
        super.onStart()

        addLifecycleObserver(onBackPressedPresenter)

        if (intent != null) {
            getModel<MainModel>().getPresenter<MainPresenter>()
                .addAction(DataAction(MainPresenter.IntentAction, intent))
        } else {
            val fragment =
                ApplicationSingleton.instance.activityProvider.getCurrentFragment<Fragment>()
            if (fragment == null) {
                getModel<MainModel>().getPresenter<MainPresenter>()
                    .addAction(ApplicationAction(MainPresenter.OnStartApplication))
            } else {
                onUserInteraction()
            }
        }

        //val data = Data.Builder()
        //    .putString("message", "Тест")
        //    .build()
        // ApplicationSingleton.instance.jobProvider.runOnce(NotificationWorker::class.java, data)

    }

    override fun onBackPressed() {
        if (!onBackPressedPresenter.onClick()) {
            super.onBackPressed()
        }
    }

    fun clearIntent() {
        intent = null
    }

    override fun onUserInteraction() {
        ApplicationSingleton.instance.onUserInteraction()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_APPLICATION_UPDATE && resultCode == RESULT_OK) {
            ShowMessageAction(getString(R.string.app_upadted)).setType(
                ApplicationUtils.MESSAGE_TYPE_SUCCESS
            )
        }
    }

    override fun onNetworkConnected() {
        super.onNetworkConnected()

        getModel<MainModel>().getPresenter<MainPresenter>()
            .addAction(ApplicationAction(MainPresenter.OnNetworkConnected))
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)

        GlideApp.with(this).onTrimMemory(level)
    }

    override fun onLowMemory() {
        super.onLowMemory()

        GlideApp.with(this).onLowMemory()
    }
}
