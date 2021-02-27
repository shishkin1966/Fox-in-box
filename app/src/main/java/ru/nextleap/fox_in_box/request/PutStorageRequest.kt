package ru.nextleap.fox_in_box.request

import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.sl.request.AbsRequest
import java.io.Serializable
import java.util.concurrent.TimeUnit

class PutStorageRequest(private val key: String, private val value: Serializable) : AbsRequest() {
    companion object {
        const val NAME = "PutStorageRequest"
    }

    override fun isDistinct(): Boolean {
        return false
    }

    override fun run() {
        ApplicationSingleton.instance.storageProvider.put(
            key,
            value,
            System.currentTimeMillis() + TimeUnit.HOURS.toMillis(2)
        )
    }

    override fun getName(): String {
        return NAME
    }

}