package ru.nextleap.fox_in_box.screen.materials

import ru.nextleap.sl.model.AbsPresenterModel


class MaterialsModel(view: MaterialsFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(MaterialsPresenter(this))
    }
}
