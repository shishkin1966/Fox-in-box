package ru.nextleap.fox_in_box.request

import io.reactivex.Single
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.data.BaseResponse
import ru.nextleap.sl.request.AbsNetResultMessageRequest

class GetNewsRequest(subscribe: String, private val newsId: Int) :
    AbsNetResultMessageRequest(subscribe) {
    companion object {
        const val NAME = "GetNewsRequest"
    }

    override fun getData(): Single<BaseResponse>? {
        return ApplicationSingleton.instance.getApi().getNews(newsId)
    }

    override fun isDistinct(): Boolean {
        return false
    }

    override fun getName(): String {
        return NAME
    }
}
