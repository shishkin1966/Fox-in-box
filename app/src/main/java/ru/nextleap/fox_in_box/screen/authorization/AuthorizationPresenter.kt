package ru.nextleap.fox_in_box.screen.authorization

import ru.nextleap.sl.action.IAction
import ru.nextleap.sl.data.ExtResult
import ru.nextleap.sl.presenter.AbsModelPresenter
import ru.nextleap.sl.request.IResponseListener

class AuthorizationPresenter(model: AuthorizationModel) : AbsModelPresenter(model),
    IResponseListener {

    companion object {
        const val NAME = "AuthorizationPresenter"
    }

    override fun isRegister(): Boolean {
        return true
    }

    override fun response(result: ExtResult) {
    }

    override fun getName(): String {
        return NAME
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        return false
    }

}
