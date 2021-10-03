package ru.nextleap.fox_in_box

import android.annotation.SuppressLint
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import ru.nextleap.fox_in_box.provider.*
import ru.nextleap.sl.AbsServiceLocator
import ru.nextleap.sl.IProviderFactory
import ru.nextleap.sl.observe.NetObservable
import ru.nextleap.sl.observe.ObjectObservable
import ru.nextleap.sl.observe.ScreenBroadcastReceiverObservable
import ru.nextleap.sl.provider.*
import ru.nextleap.sl.task.CommonExecutor
import ru.nextleap.sl.task.NetExecutor

object ServiceLocatorSingleton {
    val instance = ServiceLocator()
}

class ServiceLocator : AbsServiceLocator() {
    companion object {
        const val NAME = "ServiceLocator"
    }

    override fun getName(): String {
        return NAME
    }

    @SuppressLint("CheckResult")
    override fun start() {
        registerProvider(ErrorSingleton.instance)
        registerProvider(ApplicationSingleton.instance)

        val providers: ArrayList<String> = ArrayList()
        providers.add(CrashProvider.NAME)
        providers.add(AnalyticsProvider.NAME)
        providers.add(WakeLockProvider.NAME)
        providers.add(PresenterUnion.NAME)
        providers.add(DesktopProvider.NAME)
        providers.add(ObservableUnion.NAME)
        providers.add(NetExecutor.NAME)
        providers.add(CommonExecutor.NAME)
        providers.add(SessionProvider.NAME)
        providers.add(NetProvider.NAME)

        Observable.fromIterable(providers)
            .subscribeOn(Schedulers.io())
            .map { provider -> registerProvider(provider) }
            .toList()
            .blockingGet()

        val union = get<IObservableUnion>(ObservableUnion.NAME)
        union?.register(NetObservable())
        union?.register(ObjectObservable())
        union?.register(ScreenBroadcastReceiverObservable())
    }

    override fun getProviderFactory(): IProviderFactory {
        return ProviderFactorySingleton.instance
    }

}
