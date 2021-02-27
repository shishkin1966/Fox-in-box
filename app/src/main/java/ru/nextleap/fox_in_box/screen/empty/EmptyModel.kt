package ru.nextleap.fox_in_box.screen.empty

import ru.nextleap.sl.model.AbsPresenterModel


class EmptyModel(view: EmptyFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(EmptyPresenter(this))
    }
}
