package ru.nextleap.fox_in_box.screen.products

import ru.nextleap.sl.model.AbsPresenterModel


class ProductsModel(view: ProductsFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(ProductsPresenter(this))
    }
}
