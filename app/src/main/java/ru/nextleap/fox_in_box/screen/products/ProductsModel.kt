package ru.nextleap.fox_in_box.screen.products

import android.annotation.SuppressLint
import ru.nextleap.fox_in_box.data.SKU
import ru.nextleap.fox_in_box.screen.IItemsList
import ru.nextleap.fox_in_box.screen.orders.OrdersFragment
import ru.nextleap.sl.action.handler.FragmentActionHandler
import ru.nextleap.sl.model.AbsPresenterModel


class ProductsModel(view: ProductsFragment) : AbsPresenterModel(view), IItemsList<SKU> {
    init {
        setPresenter(ProductsPresenter(this))
    }

    fun getHandler() : FragmentActionHandler {
        return getView<ProductsFragment>().actionHandler
    }

    override fun addAllItems(data: ArrayList<SKU>) {
        getView<ProductsFragment>().adapter.setItems(data)
    }

    override fun addItems(data: ArrayList<SKU>) {
        getView<ProductsFragment>().adapter.addAll(data)
    }

    override fun clearItems() {
        getView<ProductsFragment>().adapter.clear()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun dataChanged() {
        getView<ProductsFragment>().adapter.notifyDataSetChanged()
    }

}
