package ru.nextleap.sl

import ru.nextleap.sl.provider.ApplicationProvider
import java.util.concurrent.TimeUnit


abstract class AbsShortLiveProvider : AbsProvider(), AutoCompleteHandler.OnShutdownListener {
    private val serviceHandler: AutoCompleteHandler<Boolean> =
        AutoCompleteHandler("AbsShortLiveProvider [" + getName() + "]")
    private val TIMEUNIT = TimeUnit.SECONDS
    private val TIMEUNIT_DURATION = 10L

    init {
        serviceHandler.setOnShutdownListener(this)
        serviceHandler.post(true)
        setShutdownTimeout(TIMEUNIT.toMillis(TIMEUNIT_DURATION))
    }

    fun setShutdownTimeout(shutdownTimeout: Long) {
        if (shutdownTimeout > 0) {
            serviceHandler.setShutdownTimeout(shutdownTimeout)
        }
    }

    fun post() {
        serviceHandler.post(true)
    }

    override fun onShutdown(handler: AutoCompleteHandler<*>) {
        ApplicationProvider.serviceLocator?.unregisterProvider(getName())
    }

}
