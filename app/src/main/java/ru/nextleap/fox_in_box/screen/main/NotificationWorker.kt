package ru.nextleap.fox_in_box.screen.main

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.nextleap.common.allTrim
import ru.nextleap.fox_in_box.ApplicationSingleton

class NotificationWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        val title = inputData.getString("title")
        val message = inputData.getString("message").allTrim()

        ApplicationSingleton.instance.notificationProvider.addNotification(title, message)

        return Result.success()
    }
}