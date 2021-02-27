package ru.nextleap.fox_in_box.screen.orders

import ru.nextleap.sl.model.AbsPresenterModel


class OrdersModel(view: OrdersFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(OrdersPresenter(this))
    }
}
