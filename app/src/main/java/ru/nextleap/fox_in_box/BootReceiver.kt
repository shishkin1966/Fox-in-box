package ru.nextleap.fox_in_box

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        ApplicationSingleton.instance.onBoot()
    }
}