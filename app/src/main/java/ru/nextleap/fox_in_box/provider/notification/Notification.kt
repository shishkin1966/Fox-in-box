package ru.nextleap.fox_in_box.provider.notification


import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import ru.nextleap.common.ApplicationUtils
import ru.nextleap.common.mid
import ru.nextleap.common.numToken
import ru.nextleap.common.token
import ru.nextleap.fox_in_box.ApplicationConstant
import ru.nextleap.fox_in_box.R
import ru.nextleap.fox_in_box.screen.main.MainActivity
import ru.nextleap.sl.provider.ApplicationProvider

class Notification() : INotificationShortProvider {
    private val GROUP_NAME = ApplicationProvider.appContext.getString(R.string.app_name)
    private var id = -1
    private val nm: NotificationManager = ApplicationUtils.getSystemService(
        ApplicationProvider.appContext,
        Context.NOTIFICATION_SERVICE
    )

    override fun addNotification(title: String?, message: String) {
        id = System.currentTimeMillis().toInt()
        show(title, message)
    }

    override fun replaceNotification(title: String?, message: String) {
        if (id == -1) {
            id = System.currentTimeMillis().toInt()
        }
        show(title, message)
    }

    private fun show(title: String?, message: String) {
        val context = ApplicationProvider.appContext

        var pendingIntent: PendingIntent? = null
        var intent: Intent?
        if (message.contains("https://")) {
            try {
                val tokens: Int = message.numToken(" ")
                for (i in 1..tokens) {
                    var token: String = message.token(" ", i)
                    if (token.startsWith("https://")) {
                        if (token.mid(token.length - 1).equals(".")) {
                            token = token.mid(0, token.length - 1)
                        }
                        intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(token)
                        pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
                        break
                    }
                }
            } catch (e: Exception) {
            }
        }
        if (pendingIntent == null) {
            intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            intent.action = ApplicationConstant.NOTIFICATION_CLICK
            pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT
            )
        }

        val notificationBuilder = NotificationCompat.Builder(context, GROUP_NAME)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setWhen(System.currentTimeMillis())
            .setDefaults(0)
            .setContentText(message)
        if (!title.isNullOrEmpty()) {
            notificationBuilder.setContentTitle(title)
        } else {
            notificationBuilder.setContentTitle(ApplicationProvider.appContext.getString(R.string.app_name))
        }
        nm.notify(id, notificationBuilder.build())
    }

    override fun clear() {
        nm.cancelAll()
    }

    override fun getId(): Int {
        return id
    }

    override fun setId(id: Int) {
        this.id = id
    }
}
