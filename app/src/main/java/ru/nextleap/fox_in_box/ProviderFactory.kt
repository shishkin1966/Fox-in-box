package ru.nextleap.fox_in_box

import ru.nextleap.fox_in_box.provider.*
import ru.nextleap.fox_in_box.provider.notification.NotificationProvider
import ru.nextleap.sl.INamed
import ru.nextleap.sl.IProvider
import ru.nextleap.sl.IProviderFactory
import ru.nextleap.sl.provider.*
import ru.nextleap.sl.task.CommonExecutor
import ru.nextleap.sl.task.NetExecutor

object ProviderFactorySingleton {
    val instance = ProviderFactory()
}

class ProviderFactory : IProviderFactory, INamed {
    companion object {
        const val NAME = "ProviderFactory"
    }

    override fun create(name: String): IProvider? {
        return try {
            when (name) {
                ErrorProvider.NAME -> ErrorSingleton.instance
                CrashProvider.NAME -> CrashProvider()
                ApplicationSingleton.instance.getName() -> ApplicationSingleton.instance
                ActivityUnion.NAME -> ActivityUnion()
                PresenterUnion.NAME -> PresenterUnion()
                MessengerUnion.NAME -> MessengerUnion()
                ObservableUnion.NAME -> ObservableUnion()
                CommonExecutor.NAME -> CommonExecutor()
                NetExecutor.NAME -> NetExecutor()
                SessionProvider.NAME -> SessionProvider()
                NetProvider.NAME -> NetProvider()
                DesktopProvider.NAME -> DesktopProvider()
                SecureProvider.NAME -> SecureProvider()
                NotificationProvider.NAME -> NotificationProvider()
                NetImageProvider.NAME -> NetImageProvider()
                StorageProvider.NAME -> StorageProvider()
                WakeLockProvider.NAME -> WakeLockProvider()
                else -> Class.forName(name).newInstance() as IProvider
            }
        } catch (e: Exception) {
            ErrorSingleton.instance.onError(getName(), e)
            null
        }
    }

    override fun getName(): String {
        return NAME
    }

}
