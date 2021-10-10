package ru.nextleap.fox_in_box.screen.home

import ru.nextleap.sl.model.AbsPresenterModel


class HomeModel(view: HomeFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(HomePresenter(this))
    }

    fun showNews() {
        getView<HomeFragment>().showNews()
    }

    fun checkUpdate() {
        getView<HomeFragment>().checkUpdate()
    }

}
