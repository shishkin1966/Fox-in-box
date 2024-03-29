package ru.nextleap.sl.provider

import android.util.Log
import android.widget.Toast
import ru.nextleap.common.ApplicationUtils
import ru.nextleap.sl.AbsProvider
import ru.nextleap.sl.IProvider
import ru.nextleap.sl.data.ExtError


object LogSingleton {
    val instance = LogProvider()
}

class LogProvider : AbsProvider(), ILogProvider {
    companion object {
        const val NAME = "LogProvider"
    }

    override fun info(source: String, info: String?) {
        if (!info.isNullOrEmpty()) {
            Log.i(source, info)
        }
    }

    override fun onError(source: String, e: Exception) {
        Log.e(source, Log.getStackTraceString(e))

        ApplicationUtils.showToast(
            ApplicationProvider.appContext,
            e.message,
            Toast.LENGTH_LONG,
            ApplicationUtils.MESSAGE_TYPE_ERROR
        )
    }

    override fun onError(source: String, throwable: Throwable) {
        Log.e(source, Log.getStackTraceString(throwable))

        ApplicationUtils.showToast(
            ApplicationProvider.appContext,
            throwable.message,
            Toast.LENGTH_LONG,
            ApplicationUtils.MESSAGE_TYPE_ERROR
        )
    }

    override fun onError(source: String, e: Exception, displayMessage: String?) {
        Log.e(source, Log.getStackTraceString(e))

        if (!displayMessage.isNullOrEmpty()) {
            ApplicationUtils.showToast(
                ApplicationProvider.appContext,
                displayMessage,
                Toast.LENGTH_LONG,
                ApplicationUtils.MESSAGE_TYPE_ERROR
            )
        }
    }

    override fun onError(source: String, message: String?, isDisplay: Boolean) {
        if (!message.isNullOrEmpty()) {
            Log.e(source, message)

            if (isDisplay) {
                ApplicationUtils.showToast(
                    ApplicationProvider.appContext,
                    message,
                    Toast.LENGTH_LONG,
                    ApplicationUtils.MESSAGE_TYPE_ERROR
                )
            }
        }
    }

    override fun onError(error: ExtError) {
        if (error.hasError()) {
            ApplicationUtils.showToast(
                ApplicationProvider.appContext,
                error.getErrorText(),
                Toast.LENGTH_LONG,
                ApplicationUtils.MESSAGE_TYPE_ERROR
            )
        }
    }

    override fun getName(): String {
        return NAME
    }

    override fun isPersistent(): Boolean {
        return true
    }

    override operator fun compareTo(other: IProvider): Int {
        return if (other is ILogProvider) 0 else 1
    }

}
