package ru.nextleap.fox_in_box.screen.news

import ru.nextleap.sl.model.AbsPresenterModel


class NewsModel(view: NewsFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(NewsPresenter(this))
    }
}
