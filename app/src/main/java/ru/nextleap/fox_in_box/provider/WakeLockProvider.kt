package ru.nextleap.fox_in_box.provider

import android.content.Context
import android.os.PowerManager
import ru.nextleap.fox_in_box.BuildConfig
import ru.nextleap.sl.AbsProvider
import ru.nextleap.sl.IProvider
import ru.nextleap.sl.provider.ApplicationProvider

class WakeLockProvider : AbsProvider(), IWakeLockProvider {
    companion object {
        const val NAME = "WakeLockProvider"
    }

    private var wakeLock: PowerManager.WakeLock? = null

    override fun acquire() {
        release()

        val pm =
            ApplicationProvider.appContext.getSystemService(Context.POWER_SERVICE) as PowerManager
        wakeLock =
            pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, BuildConfig.APPLICATION_ID + ":" + NAME)
        wakeLock?.acquire(60 * 1000L /*1 minute*/)
    }

    override fun release() {
        if (wakeLock != null) {
            wakeLock!!.release()
        }
        wakeLock = null
    }

    override fun getName(): String {
        return NAME
    }

    override operator fun compareTo(other: IProvider): Int {
        return if (other is IWakeLockProvider) 0 else 1
    }
}