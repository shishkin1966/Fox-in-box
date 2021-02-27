package ru.nextleap.fox_in_box.screen.registration

import ru.nextleap.sl.presenter.AbsModelPresenter

class RegistrationPresenter(model: RegistrationModel) : AbsModelPresenter(model) {

    companion object {
        const val NAME = "RegistrationPresenter"
    }

    override fun isRegister(): Boolean {
        return false
    }

    override fun getName(): String {
        return NAME
    }

}
