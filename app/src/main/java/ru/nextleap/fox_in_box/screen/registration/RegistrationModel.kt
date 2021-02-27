package ru.nextleap.fox_in_box.screen.registration

import ru.nextleap.sl.model.AbsPresenterModel


class RegistrationModel(view: RegistrationFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(RegistrationPresenter(this))
    }
}
