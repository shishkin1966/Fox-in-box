package ru.nextleap.fox_in_box.request

import io.reactivex.Single
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.data.Token
import ru.nextleap.sl.request.AbsNetResultMessageRequest

class GetTokenRequest(subscribe: String) : AbsNetResultMessageRequest(subscribe) {
    companion object {
        const val NAME = "GetTokenRequest"
    }

    override fun getData(): Single<Token>? {
        return ApplicationSingleton.instance.getApi().getToken(
            "password",
            ApplicationSingleton.instance.sessionProvider.getLogin(),
            ApplicationSingleton.instance.sessionProvider.getPsw()
        )
    }

    override fun isDistinct(): Boolean {
        return true
    }

    override fun getName(): String {
        return NAME
    }
}
