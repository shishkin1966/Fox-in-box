package ru.nextleap.sl.provider

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import ru.nextleap.sl.IProvider
import ru.nextleap.sl.IServiceLocator


open class ApplicationProvider : MultiDexApplication(),
    IApplicationProvider {
    private var isExit = false

    companion object {
        const val NAME = "ApplicationProvider"
        val instance = ApplicationProvider()
        lateinit var appContext: Context
        var serviceLocator: IServiceLocator? = null
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext
    }

    override fun isPersistent(): Boolean {
        return true
    }

    override fun onUnRegister() = Unit

    override fun onRegister() = Unit

    override fun stop() {
        isExit = true

        if (serviceLocator != null) {
            val union = serviceLocator?.get<ActivityUnion>(
                ActivityUnion.NAME
            )
            union!!.stop()
        }
    }

    override fun getName(): String {
        return NAME
    }

    override fun isValid(): Boolean {
        return !isExit
    }

    override fun compareTo(other: IProvider): Int {
        return if (other is IApplicationProvider) 0 else 1
    }

    override fun isExit(): Boolean {
        return isExit
    }

    override fun setStart() {
        isExit = true
    }

}
