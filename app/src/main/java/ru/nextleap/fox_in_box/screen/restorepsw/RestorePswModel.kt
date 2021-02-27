package ru.nextleap.fox_in_box.screen.restorepsw

import ru.nextleap.sl.model.AbsPresenterModel


class RestorePswModel(view: RestorePswFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(RestorePswPresenter(this))
    }
}
