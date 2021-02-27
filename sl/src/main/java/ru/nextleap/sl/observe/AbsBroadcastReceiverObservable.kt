package ru.nextleap.sl.observe

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import ru.nextleap.sl.provider.ApplicationProvider


abstract class AbsBroadcastReceiverObservable : AbsObservable() {
    private var intentFilter: IntentFilter = getIntentFilter()
    private var broadcastReceiver: BroadcastReceiver? = null

    abstract fun getIntentFilter(): IntentFilter

    override fun register() {
        val context = ApplicationProvider.appContext
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                onChange(intent)
            }
        }
        context.registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun unregister() {
        if (broadcastReceiver != null) {
            val context = ApplicationProvider.appContext
            context.unregisterReceiver(broadcastReceiver)
            broadcastReceiver = null
        }
    }

    override fun stop() {
        unregister()
    }

}
