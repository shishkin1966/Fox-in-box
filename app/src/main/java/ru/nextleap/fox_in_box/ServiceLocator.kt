package ru.nextleap.fox_in_box

import ru.nextleap.fox_in_box.provider.DesktopProvider
import ru.nextleap.fox_in_box.provider.NetProvider
import ru.nextleap.fox_in_box.provider.SessionProvider
import ru.nextleap.fox_in_box.provider.WakeLockProvider
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

    override fun start() {
        registerProvider(ErrorSingleton.instance)
        registerProvider(ApplicationSingleton.instance)
        registerProvider(CrashProvider.NAME)
        registerProvider(WakeLockProvider.NAME)
        registerProvider(PresenterUnion.NAME)
        registerProvider(DesktopProvider.NAME)

        registerProvider(ObservableUnion.NAME)
        val union = get<IObservableUnion>(ObservableUnion.NAME)
        union?.register(NetObservable())
        union?.register(ObjectObservable())
        union?.register(ScreenBroadcastReceiverObservable())

        registerProvider(NetExecutor.NAME)
        registerProvider(CommonExecutor.NAME)

        registerProvider(SessionProvider.NAME)
        registerProvider(NetProvider.NAME)
    }

    override fun getProviderFactory(): IProviderFactory {
        return ProviderFactorySingleton.instance
    }

}
