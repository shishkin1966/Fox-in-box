package ru.nextleap.fox_in_box.provider

import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.request.*
import ru.nextleap.sl.provider.ApplicationProvider

class Providers {
    companion object {
        @JvmStatic
        fun exitApplication() {
            ApplicationProvider.instance.stop()
        }

        @JvmStatic
        fun getToken(subscriber: String) {
            ApplicationSingleton.instance.get<NetProvider>(NetProvider.NAME)
                ?.request(GetTokenRequest(subscriber))
        }

        @JvmStatic
        fun getProfile(subscriber: String) {
            ApplicationSingleton.instance.get<NetProvider>(NetProvider.NAME)
                ?.request(GetProfileRequest(subscriber))
        }

        @JvmStatic
        fun getNewsList(subscriber: String, skip: Int, take: Int) {
            ApplicationSingleton.instance.get<NetProvider>(NetProvider.NAME)
                ?.request(GetNewsListRequest(subscriber, skip, take))
        }

        @JvmStatic
        fun getNews(subscriber: String, id: Int) {
            ApplicationSingleton.instance.get<NetProvider>(NetProvider.NAME)
                ?.request(GetNewsRequest(subscriber, id))
        }

        @JvmStatic
        fun getMaterialsList(subscriber: String, skip: Int, take: Int) {
            ApplicationSingleton.instance.get<NetProvider>(NetProvider.NAME)
                ?.request(GetMaterialsListRequest(subscriber, skip, take))
        }

        @JvmStatic
        fun getSKUList(subscriber: String, skip: Int, take: Int) {
            ApplicationSingleton.instance.get<NetProvider>(NetProvider.NAME)
                ?.request(GetSKUListRequest(subscriber, skip, take))
        }

        @JvmStatic
        fun getAggregatedList(subscriber: String, skip: Int, take: Int) {
            ApplicationSingleton.instance.get<NetProvider>(NetProvider.NAME)
                ?.request(GetAggregatedListRequest(subscriber, skip, take))
        }

        @JvmStatic
        fun getOrdersList(subscriber: String, skip: Int, take: Int) {
            ApplicationSingleton.instance.get<NetProvider>(NetProvider.NAME)
                ?.request(GetOrdersListRequest(subscriber, skip, take))
        }
    }
}
