package ru.nextleap.fox_in_box.screen.home

import ru.nextleap.sl.action.ApplicationAction
import ru.nextleap.sl.action.IAction
import ru.nextleap.sl.presenter.AbsModelPresenter

class HomePresenter(model: HomeModel) : AbsModelPresenter(model) {

    companion object {
        const val NAME = "HomePresenter"
        const val ShowNews = "ShowNews"
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (action is ApplicationAction) when (action.getName()) {
            ShowNews -> {
                getModel<HomeModel>().showNews()
                return true
            }
        }
        return false
    }

    override fun isRegister(): Boolean {
        return true
    }

    override fun getName(): String {
        return NAME
    }

    override fun onStart() {
        getModel<HomeModel>().checkUpdate()
    }


}
