@file:Suppress("unused", "UNUSED_PARAMETER")

package ru.nextleap.fox_in_box

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import ru.nextleap.common.Connectivity
import ru.nextleap.common.onlyChar
import ru.nextleap.fox_in_box.provider.*
import ru.nextleap.fox_in_box.provider.notification.INotificationProvider
import ru.nextleap.fox_in_box.provider.notification.NotificationProvider
import ru.nextleap.sl.IProvider
import ru.nextleap.sl.PreferencesUtils
import ru.nextleap.sl.message.IMessage
import ru.nextleap.sl.observe.NetObservable
import ru.nextleap.sl.observe.ObjectObservable
import ru.nextleap.sl.presenter.IPresenter
import ru.nextleap.sl.provider.*
import ru.nextleap.sl.task.CommonExecutor
import ru.nextleap.sl.task.NetExecutor
import ru.nextleap.sl.ui.IActivity


object ApplicationSingleton {
    val instance = App()
}

class App : ApplicationProvider(), LifecycleObserver {
    private var isBackground: Boolean = false

    override fun onCreate() {
        super.onCreate()

        ServiceLocatorSingleton.instance.start()

        serviceLocator = ServiceLocatorSingleton.instance

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun stop() {
        ApplicationSingleton.instance.netProvider.setToken(null)

        super.stop()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        isBackground = true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        onChange(
            NetObservable.NAME,
            Connectivity.isNetworkConnected(appContext)
        )
        isBackground = false
        sessionProvider.onUserInteraction()
    }

    // событие загрузка приложения при Boot телефона
    fun onBoot() {
    }

    fun <C : IProvider> get(name: String): C? {
        return serviceLocator?.get(name)
    }

    fun <C : IPresenter> getPresenter(name: String): C? {
        val union = serviceLocator?.get<IPresenterUnion>(PresenterUnion.NAME)
        return union?.getPresenter(name)
    }

    var observableProvider: IObservableUnion
        get() = get(ObservableUnion.NAME)!!
        private set(value) {}

    var activityProvider: IActivityUnion
        get() = get(ActivityUnion.NAME)!!
        private set(value) {}

    var routerProvider: IRouterProvider
        get() = activityProvider.getActivity<IActivity>() as IRouterProvider
        private set(value) {}

    var sessionProvider: ISessionProvider
        get() = get(SessionProvider.NAME)!!
        private set(value) {}

    var desktopProvider: IDesktopProvider
        get() = get(DesktopProvider.NAME)!!
        private set(value) {}

    var netProvider: NetProvider
        get() = get(NetProvider.NAME)!!
        private set(value) {}

    var netImageProvider: INetImageProvider
        get() = get(NetImageProvider.NAME)!!
        private set(value) {}

    var storageProvider: IStorageProvider
        get() = get(StorageProvider.NAME)!!
        private set(value) {}

    var netExecutor: NetExecutor
        get() = get(NetExecutor.NAME)!!
        private set(value) {}

    var commonExecutor: CommonExecutor
        get() = get(CommonExecutor.NAME)!!
        private set(value) {}

    var secureProvider: ISecureProvider
        get() = get(SecureProvider.NAME)!!
        private set(value) {}

    var notificationProvider: INotificationProvider
        get() = get(NotificationProvider.NAME)!!
        private set(value) {}

    var wakeLockProvider: IWakeLockProvider
        get() = get(WakeLockProvider.NAME)!!
        private set(value) {}

    var analiticsProvider: IAnalyticsProvider
        get() = get(AnalyticsProvider.NAME)!!
        private set(value) {}

    fun getApi(): NetApi {
        return ApplicationSingleton.instance.get<NetProvider>(NetProvider.NAME)!!.getApi()
    }

    // Событие - изменился observable объект с именем observable. Объект изменения obj
    fun onChange(observable: String, obj: Any) {
        observableProvider.getObservable(observable)?.onChange(obj)
    }

    // Событие - изменился объект с именем name
    fun onChange(name: String) {
        observableProvider.getObservable(ObjectObservable.NAME)?.onChange(name)
    }

    override fun getName(): String {
        return BuildConfig.APPLICATION_ID
    }

    fun onUserInteraction() {
        val provider = serviceLocator?.get<ISessionProvider>(SessionProvider.NAME)
        provider?.onUserInteraction()
    }

    fun onError(source: String, message: String?, isDisplay: Boolean) {
        val union = serviceLocator?.get<IErrorProvider>(ErrorProvider.NAME)
        union?.onError(source, message, isDisplay)
    }

    fun onError(source: String, e: Exception) {
        val union = serviceLocator?.get<IErrorProvider>(ErrorProvider.NAME)
        union?.onError(source, e)
    }

    // послать почтовое сообщение
    fun addMessage(message: IMessage) {
        val union = serviceLocator?.get<IMessengerUnion>(MessengerUnion.NAME)
        union?.addMessage(message)
    }

    // послать почтовое сообщение напрямую получателю. В случае его отсутствия отбросить сообщение
    fun addNotMandatoryMessage(message: IMessage) {
        val union = serviceLocator?.get<IMessengerUnion>(MessengerUnion.NAME)
        union?.addNotMandatoryMessage(message)
    }

    fun newImageHeight(url: String, width: Int): Int {
        val w = PreferencesUtils.getInt(appContext, url.onlyChar() + "_width")
        val h = PreferencesUtils.getInt(appContext, url.onlyChar() + "_height")
        return (width * h) / w
    }

}
