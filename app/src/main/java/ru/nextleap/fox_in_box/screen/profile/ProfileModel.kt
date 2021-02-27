package ru.nextleap.fox_in_box.screen.profile

import ru.nextleap.sl.model.AbsPresenterModel


class ProfileModel(view: ProfileFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(ProfilePresenter(this))
    }
}
