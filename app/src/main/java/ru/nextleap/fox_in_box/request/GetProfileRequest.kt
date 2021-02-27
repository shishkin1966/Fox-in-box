package ru.nextleap.fox_in_box.request

import io.reactivex.Single
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.data.BaseResponse
import ru.nextleap.sl.request.AbsNetResultMessageRequest

class GetProfileRequest(subscribe: String) : AbsNetResultMessageRequest(subscribe) {
    companion object {
        const val NAME = "GetProfileRequest"
    }

    override fun getData(): Single<BaseResponse>? {
        return ApplicationSingleton.instance.getApi().getProfile()
    }

    override fun isDistinct(): Boolean {
        return false
    }

    override fun getName(): String {
        return NAME
    }
}
