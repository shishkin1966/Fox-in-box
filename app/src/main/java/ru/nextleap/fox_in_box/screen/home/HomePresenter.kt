package ru.nextleap.fox_in_box.screen.home

import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import ru.nextleap.fox_in_box.ApplicationConstant.Companion.REQUEST_APPLICATION_UPDATE
import ru.nextleap.sl.action.ApplicationAction
import ru.nextleap.sl.action.IAction
import ru.nextleap.sl.presenter.AbsModelPresenter

class HomePresenter(model: HomeModel) : AbsModelPresenter(model) {

    companion object {
        const val NAME = "HomePresenter"
        const val ShowNews = "ShowNews"
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (action is ApplicationAction) when (action.getName()) {
            ShowNews -> {
                getView<HomeFragment>().showNews()
                return true
            }
        }
        return false
    }

    override fun isRegister(): Boolean {
        return true
    }

    override fun getName(): String {
        return NAME
    }

    override fun onStart() {
        checkUpdate()
    }

    private fun checkUpdate() {
        val activity = getView<HomeFragment>().activity
        if (activity != null) {
            val appUpdateManager = AppUpdateManagerFactory.create(activity)
            val appUpdateInfoTask = appUpdateManager.appUpdateInfo
            appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
                ) {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.FLEXIBLE,
                        activity,
                        REQUEST_APPLICATION_UPDATE
                    )
                }
            }
        }
    }


}
