package ru.nextleap.fox_in_box.screen.authorization

import ru.nextleap.sl.model.AbsPresenterModel


class AuthorizationModel(view: AuthorizationFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(AuthorizationPresenter(this))
    }
}
