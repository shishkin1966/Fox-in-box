package ru.nextleap.fox_in_box.screen.profile

import ru.nextleap.fox_in_box.screen.products.ProductsFragment
import ru.nextleap.sl.action.handler.FragmentActionHandler
import ru.nextleap.sl.model.AbsPresenterModel


class ProfileModel(view: ProfileFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(ProfilePresenter(this))
    }

    fun getHandler() : FragmentActionHandler {
        return getView<ProfileFragment>().actionHandler
    }

}
