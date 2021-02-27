package ru.nextleap.sl.observe

import android.content.Intent
import android.content.IntentFilter


class ScreenBroadcastReceiverObservable : AbsBroadcastReceiverObservable() {
    companion object {
        const val NAME = "ScreenBroadcastReceiverObservable"
    }

    override fun getName(): String {
        return NAME
    }

    override fun getIntentFilter(): IntentFilter {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_SCREEN_ON)
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF)
        return intentFilter
    }

}
