package ru.nextleap.fox_in_box.screen.view_news

import ru.nextleap.sl.model.AbsPresenterModel


class ViewNewsModel(view: ViewNewsFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(ViewNewsPresenter(this))
    }
}
