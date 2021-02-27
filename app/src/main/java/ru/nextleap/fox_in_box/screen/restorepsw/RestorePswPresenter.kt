package ru.nextleap.fox_in_box.screen.restorepsw

import ru.nextleap.sl.presenter.AbsModelPresenter

class RestorePswPresenter(model: RestorePswModel) : AbsModelPresenter(model) {

    companion object {
        const val NAME = "RestorePswPresenter"
    }

    override fun isRegister(): Boolean {
        return false
    }

    override fun getName(): String {
        return NAME
    }

}
