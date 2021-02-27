package ru.nextleap.fox_in_box

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.nextleap.sl.PreferencesUtils
import ru.nextleap.sl.provider.ApplicationProvider

class PushFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        ApplicationSingleton.instance.wakeLockProvider.acquire()

//        if (remoteMessage.data.isNotEmpty()) {
//        }

        if (remoteMessage.notification != null) {
            remoteMessage.notification!!.body?.let {
                ApplicationSingleton.instance.notificationProvider.addNotification(
                    remoteMessage.notification?.title, it
                )
            }
        }

        ApplicationSingleton.instance.wakeLockProvider.release()
    }

    override fun onNewToken(token: String) {
        ApplicationSingleton.instance.wakeLockProvider.acquire()

        sendRegistrationToServer(token)

        ApplicationSingleton.instance.wakeLockProvider.release()
    }

    private fun sendRegistrationToServer(token: String) {
        PreferencesUtils.putString(
            ApplicationProvider.appContext,
            ApplicationConstant.PushToken,
            token
        )
    }

}