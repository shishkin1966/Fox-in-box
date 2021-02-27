package ru.nextleap.fox_in_box.provider

import ru.nextleap.sl.IProvider
import ru.nextleap.sl.provider.AbsNetProvider
import ru.nextleap.sl.provider.INetProvider

class NetProvider : AbsNetProvider<NetApi>() {
    companion object {
        const val NAME = "NetProvider"
        const val URL = "http://promo.ofrs.su/" //Базовый адрес
    }

    override fun getApiClass(): Class<NetApi> {
        return NetApi::class.java
    }

    override fun getBaseUrl(): String {
        return URL
    }

    override fun getName(): String {
        return NAME
    }

    override fun compareTo(other: IProvider): Int {
        return if (other is INetProvider<*>) 0 else 1
    }
}
