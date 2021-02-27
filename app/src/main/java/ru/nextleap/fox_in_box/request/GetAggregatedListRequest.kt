package ru.nextleap.fox_in_box.request

import io.reactivex.Single
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.data.BaseResponse
import ru.nextleap.sl.request.AbsNetResultMessageRequest

class GetAggregatedListRequest(subscribe: String, private val skip: Int, private val take: Int) :
    AbsNetResultMessageRequest(subscribe) {
    companion object {
        const val NAME = "GetAggregatedListRequest"
    }

    override fun getData(): Single<BaseResponse>? {
        return ApplicationSingleton.instance.getApi().getAggregatedList(skip, take)
    }

    override fun isDistinct(): Boolean {
        return false
    }

    override fun getName(): String {
        return NAME
    }
}
