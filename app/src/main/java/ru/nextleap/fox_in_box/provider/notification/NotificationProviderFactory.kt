package ru.nextleap.fox_in_box.provider.notification

import ru.nextleap.common.ApplicationUtils

object NotificationProviderFactory {

    fun get(): INotificationShortProvider {
        if (ApplicationUtils.hasO()) {
            return NotificationO()
        } else if (ApplicationUtils.hasN()) {
            return NotificationN()
        }
        return Notification()
    }
}
