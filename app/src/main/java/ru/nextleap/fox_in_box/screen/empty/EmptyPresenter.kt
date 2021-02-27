package ru.nextleap.fox_in_box.screen.empty

import ru.nextleap.sl.presenter.AbsModelPresenter

class EmptyPresenter(model: EmptyModel) : AbsModelPresenter(model) {

    companion object {
        const val NAME = "EmptyPresenter"
    }

    override fun isRegister(): Boolean {
        return false
    }

    override fun getName(): String {
        return NAME
    }

}
