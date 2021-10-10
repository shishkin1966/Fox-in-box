package ru.nextleap.fox_in_box.screen.orders

import android.annotation.SuppressLint
import ru.nextleap.fox_in_box.data.Orders
import ru.nextleap.fox_in_box.screen.IItemsList
import ru.nextleap.fox_in_box.screen.materials.MaterialsFragment
import ru.nextleap.sl.action.handler.FragmentActionHandler
import ru.nextleap.sl.model.AbsPresenterModel


class OrdersModel(view: OrdersFragment) : AbsPresenterModel(view), IItemsList<Orders> {
    init {
        setPresenter(OrdersPresenter(this))
    }

    fun getHandler() : FragmentActionHandler {
        return getView<OrdersFragment>().actionHandler
    }

    override fun addAllItems(data: ArrayList<Orders>) {
        getView<OrdersFragment>().adapter.setItems(data)
    }

    override fun addItems(data: ArrayList<Orders>) {
        getView<OrdersFragment>().adapter.addAll(data)
    }

    override fun clearItems() {
        getView<OrdersFragment>().adapter.clear()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun dataChanged() {
        getView<OrdersFragment>().adapter.notifyDataSetChanged()
    }

}
