package ru.nextleap.fox_in_box.provider

import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.data.Token
import ru.nextleap.fox_in_box.screen.main.MainPresenter
import ru.nextleap.sl.AbsProvider
import ru.nextleap.sl.IProvider
import ru.nextleap.sl.PreferencesUtils
import ru.nextleap.sl.action.ApplicationAction
import ru.nextleap.sl.provider.ApplicationProvider
import java.util.concurrent.TimeUnit

class SessionProvider : AbsProvider(), ISessionProvider {
    companion object {
        const val NAME = "SessionProvider"
    }

    private var timeout: Long = TimeUnit.DAYS.toMillis(6) // 604799 = 7 days
    private var isRestorePsw: Boolean = false
    private var isSavePsw: Boolean = true
    private var isStop: Boolean = false
    private var login: String? = null
    private var psw: String? = null
    private var userInteraction: Long = 0
    private var isRegistration: Boolean = false
    private var fio: String? = null

    override fun onRegister() {
        userInteraction = System.currentTimeMillis()
    }

    override fun onUserInteraction() {
        if (!isStop) {
            if (System.currentTimeMillis() - userInteraction > timeout) {
                userInteraction = System.currentTimeMillis()
                if (!isStop) {
                    stopSession()
                }
            } else {
                userInteraction = System.currentTimeMillis()
            }
        }
    }

    override fun stopSession() {
        isStop = true
        fio = null
        ApplicationSingleton.instance.netProvider.setToken(null)
        startSession()
    }

    override fun startSession() {
        isRegistration =
            PreferencesUtils.getBoolean(ApplicationProvider.appContext, "isRegistration", false)
        isRestorePsw =
            PreferencesUtils.getBoolean(ApplicationProvider.appContext, "isRestorePsw", false)
        isSavePsw =
            PreferencesUtils.getBoolean(ApplicationProvider.appContext, "isSavePsw", true)
        login = ApplicationSingleton.instance.secureProvider.get("login")
        if (isSavePsw) {
            psw = ApplicationSingleton.instance.secureProvider.get("psw")
        }
        login = "Admincislink20"
        psw = "Admincislink20"
        isRegistration = true

        val presenter =
            ApplicationSingleton.instance.getPresenter<MainPresenter>(MainPresenter.NAME)
        if (!isRegistration) {
            // экран регистрации
            presenter?.addAction(ApplicationAction(MainPresenter.Registration))
        } else {
            if (isRestorePsw) {
                // экран восстановления пароля
                presenter?.addAction(ApplicationAction(MainPresenter.RestorePsw))
            } else {
                if (ApplicationSingleton.instance.netProvider.getToken().isNullOrEmpty()) {
                    if (!login.isNullOrEmpty() && !psw.isNullOrEmpty()) {
                        // имя пользователя и пароль есть
                        if (isStop) {
                            presenter?.addAction(ApplicationAction(MainPresenter.Authorization))
                        } else {
                            presenter?.addAction(ApplicationAction(MainPresenter.Connect))
                        }
                    } else {
                        // экран авторизации
                        presenter?.addAction(ApplicationAction(MainPresenter.Authorization))
                    }
                } else {
                    // уже соединен - показать главный экран
                    presenter?.addAction(ApplicationAction(MainPresenter.ShowHomeFragment))
                }
            }
        }
    }

    override fun getLogin(): String? {
        return login
    }

    override fun getPsw(): String? {
        return psw
    }

    override fun getToken(): String? {
        return ApplicationSingleton.instance.netProvider.getToken()
    }

    override fun setToken(token: Token) {
        if (!token.access_token.isNullOrEmpty()) {
            fio = token.userName
            isStop = false
            userInteraction = System.currentTimeMillis()
            if (token.expires_in != null) {
                timeout = TimeUnit.SECONDS.toMillis((token.expires_in!! * 6 / 7).toLong())
            }
            ApplicationSingleton.instance.netProvider.setToken(token.access_token)
            ApplicationSingleton.instance.eventLogin()
            PreferencesUtils.putBoolean(ApplicationProvider.appContext, "isRegistration", true)
            // сохраняем пользователи и пароль
            ApplicationSingleton.instance.secureProvider.put("login", login)
            if (isSavePsw) {
                ApplicationSingleton.instance.secureProvider.put("psw", psw)
            }
        }
    }

    override fun clearToken() {
        ApplicationSingleton.instance.netProvider.setToken(null)
    }

    override fun getFio(): String? {
        return fio
    }

    override fun getName(): String {
        return NAME
    }

    override fun compareTo(other: IProvider): Int {
        return if (other is ISessionProvider) 0 else 1
    }
}