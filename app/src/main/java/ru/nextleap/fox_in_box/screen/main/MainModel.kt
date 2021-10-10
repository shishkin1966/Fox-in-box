package ru.nextleap.fox_in_box.screen.main

import ru.nextleap.sl.action.handler.ActivityActionHandler
import ru.nextleap.sl.model.AbsPresenterModel

class MainModel(view: MainActivity) : AbsPresenterModel(view) {
    init {
        setPresenter(MainPresenter(this))
    }

    fun getHandler(): ActivityActionHandler {
        return getView<MainActivity>().actionHandler
    }

    fun getRouter() : MainRouter {
        return getView<MainActivity>().router
    }
}
