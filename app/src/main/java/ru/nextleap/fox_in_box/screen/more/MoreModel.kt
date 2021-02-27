package ru.nextleap.fox_in_box.screen.more

import ru.nextleap.sl.model.AbsPresenterModel


class MoreModel(view: MoreFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(MorePresenter(this))
    }
}
