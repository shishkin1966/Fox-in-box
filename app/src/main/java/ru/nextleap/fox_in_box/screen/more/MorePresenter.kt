package ru.nextleap.fox_in_box.screen.more

import ru.nextleap.sl.presenter.AbsModelPresenter

class MorePresenter(model: MoreModel) : AbsModelPresenter(model) {

    companion object {
        const val NAME = "MorePresenter"
    }

    override fun isRegister(): Boolean {
        return true
    }

    override fun getName(): String {
        return NAME
    }

}
