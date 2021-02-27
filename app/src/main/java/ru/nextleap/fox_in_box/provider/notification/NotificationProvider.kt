package ru.nextleap.fox_in_box.provider.notification

import ru.nextleap.sl.AbsProvider
import ru.nextleap.sl.IProvider
import ru.nextleap.sl.PreferencesUtils
import ru.nextleap.sl.provider.ApplicationProvider


class NotificationProvider : AbsProvider(), INotificationProvider {
    companion object {
        const val NAME = "NotificationProvider"
    }

    private val provider: INotificationShortProvider = NotificationProviderFactory.get()

    override fun getName(): String {
        return NAME
    }

    override fun compareTo(other: IProvider): Int {
        return if (other is INotificationProvider) 0 else 1
    }

    override fun addNotification(title: String?, message: String) {
        provider.addNotification(title, message)
    }

    override fun replaceNotification(title: String?, message: String) {
        provider.replaceNotification(title, message)
    }

    override fun clear() {
        provider.clear()
    }

    override fun stop() {
        provider.clear()
    }

    override fun onUnRegister() {
        PreferencesUtils.putInt(ApplicationProvider.appContext, NAME, getId())

        super.onUnRegister()
    }

    override fun onRegister() {
        super.onRegister()

        setId(PreferencesUtils.getInt(ApplicationProvider.appContext, NAME))
    }

    override fun getId(): Int {
        return provider.getId()
    }

    override fun setId(id: Int) {
        provider.setId(id)
    }


}
