package ru.nextleap.fox_in_box.screen.main

import android.content.Intent
import ru.nextleap.common.ApplicationUtils
import ru.nextleap.common.Connectivity
import ru.nextleap.fox_in_box.ApplicationConstant
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.R
import ru.nextleap.fox_in_box.data.News
import ru.nextleap.fox_in_box.data.Token
import ru.nextleap.fox_in_box.provider.Providers
import ru.nextleap.fox_in_box.request.GetTokenRequest
import ru.nextleap.sl.action.*
import ru.nextleap.sl.data.ExtResult
import ru.nextleap.sl.presenter.AbsModelPresenter
import ru.nextleap.sl.provider.ApplicationProvider
import ru.nextleap.sl.request.IResponseListener

class MainPresenter(model: MainModel) : AbsModelPresenter(model), IResponseListener {
    companion object {
        const val NAME = "MainPresenter"

        const val IntentAction = "IntentAction"
        const val ShowHomeFragment = "ShowHomeFragment"
        const val ShowTermsPromotionsFragment = "ShowTermsPromotionsFragment"
        const val ShowViewNewsFragment = "ShowViewNewsFragment"
        const val Connect = "Connect"
        const val Authorization = "Authorization"
        const val Registration = "Registration"
        const val RestorePsw = "RestorePsw"
        const val OnNetworkConnected = "OnNetworkConnected"
        const val OnStartApplication = "OnStartApplication"
        const val ShowProductsFragment = "ShowProductsFragment"
        const val ShowOrdersFragment = "ShowOrdersFragment"

    }

    private var isStartSession = false

    override fun isRegister(): Boolean {
        return true
    }

    override fun getName(): String {
        return NAME
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (action is ShowProgressBarAction) {
            getModel<MainModel>().getHandler().showProgressBar()
            return true
        }

        if (action is HideProgressBarAction) {
            getModel<MainModel>().getHandler().hideProgressBar()
            return true
        }

        if (action is ApplicationAction) when (action.getName()) {
            Connect -> {
                connect()
                return true
            }
            ShowHomeFragment -> {
                getModel<MainModel>().getRouter().showHomeFragment()
                return true
            }
            Authorization -> {
                getModel<MainModel>().getRouter().showAuthorizationFragment()
                return true
            }
            OnNetworkConnected, OnStartApplication -> {
                onStartApplication()
                return true
            }
            Registration -> {
                getModel<MainModel>().getRouter().showRegistrationFragment()
                return true
            }
            RestorePsw -> {
                getModel<MainModel>().getRouter().showRestorePswFragment()
                return true
            }
            ShowTermsPromotionsFragment -> {
                getModel<MainModel>().getRouter().showTermsPromotionsFragment()
                return true
            }
            ShowProductsFragment -> {
                getModel<MainModel>().getRouter().showProductsFragment()
                return true
            }
            ShowOrdersFragment -> {
                getModel<MainModel>().getRouter().showOrdersFragment()
                return true
            }
        }

        if (action is DataAction<*>) {
            when (action.getName()) {
                IntentAction -> {
                    parseIntent(action.getData() as Intent)
                    return true
                }
                ShowViewNewsFragment -> {
                    getModel<MainModel>().getRouter().showViewNewsFragment(action.getData() as News)
                    return true
                }
            }
        }

        if (action is SnackBarAction) {
            if (action.getName() == ApplicationProvider.appContext.getString(R.string.exit)) {
                Providers.exitApplication()
            }
            return true
        }

        return false
    }

    private fun parseIntent(intent: Intent) {
        when (intent.action) {
            "android.intent.action.MAIN" -> {
                onStartApplication()
            }
            ApplicationConstant.NOTIFICATION_CLICK -> {
                onStartApplication()
            }
        }
        getView<MainActivity>().clearIntent()
    }

    private fun onStartApplication() {
        if (!isStartSession) {
            if (Connectivity.isNetworkConnected(ApplicationProvider.appContext)) {
                isStartSession = true
                ApplicationSingleton.instance.sessionProvider.startSession()
            }
        }
    }

    private fun connect() {
        ApplicationUtils.runOnUiThread {
            getModel<MainModel>().getHandler().showProgressBar()
        }
        getToken()
    }

    private fun getToken() {
        Providers.getToken(getName())
    }

    override fun response(result: ExtResult) {
        ApplicationUtils.runOnUiThread {
            getModel<MainModel>().getHandler().hideProgressBar()
            if (!result.hasError()) {
                when (result.getName()) {
                    GetTokenRequest.NAME -> {
                        if ((result.getData() as Token).error.isNullOrEmpty()) {
                            ApplicationSingleton.instance.sessionProvider.setToken(result.getData() as Token)
                            // показать главный экран
                            getModel<MainModel>().getRouter().showHomeFragment()
                        } else {
                            ApplicationSingleton.instance.sessionProvider.clearToken()

                            getView<MainActivity>().addAction(
                                ShowErrorAction((result.getData() as Token).error_description)
                            )
                            ApplicationSingleton.instance.sessionProvider.startSession()
                        }
                    }
                }
            } else {
                getView<MainActivity>().addAction(
                    ShowErrorAction(result.getErrorText())
                )
            }
        }
    }

}
