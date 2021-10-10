package ru.nextleap.fox_in_box.screen.aggregated

import android.annotation.SuppressLint
import ru.nextleap.fox_in_box.data.Aggregated
import ru.nextleap.fox_in_box.screen.IItemsList
import ru.nextleap.sl.action.handler.FragmentActionHandler
import ru.nextleap.sl.model.AbsPresenterModel


class AggregatedModel(view: AggregatedFragment) : AbsPresenterModel(view), IItemsList<Aggregated> {
    init {
        setPresenter(AggregatedPresenter(this))
    }

    override fun addAllItems(data: ArrayList<Aggregated>) {
        getView<AggregatedFragment>().adapter.setItems(data)
    }

    override fun addItems(data: ArrayList<Aggregated>) {
        getView<AggregatedFragment>().adapter.addAll(data)
    }

    override fun clearItems() {
        getView<AggregatedFragment>().adapter.clear()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun dataChanged() {
        getView<AggregatedFragment>().adapter.notifyDataSetChanged()
    }

    fun getHandler() : FragmentActionHandler {
        return getView<AggregatedFragment>().actionHandler
    }
}
