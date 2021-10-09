package ru.nextleap.fox_in_box.screen.materials

import android.annotation.SuppressLint
import ru.nextleap.fox_in_box.data.Materials
import ru.nextleap.fox_in_box.screen.IItemsList
import ru.nextleap.sl.model.AbsPresenterModel


class MaterialsModel(view: MaterialsFragment) : AbsPresenterModel(view), IItemsList<Materials> {
    init {
        setPresenter(MaterialsPresenter(this))
    }

    override fun addAllItems(data: ArrayList<Materials>) {
        getView<MaterialsFragment>().adapter.setItems(data)
    }

    override fun addItems(data: ArrayList<Materials>) {
        getView<MaterialsFragment>().adapter.addAll(data)
    }

    override fun clearItems() {
        getView<MaterialsFragment>().adapter.clear()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun dataChanged() {
        getView<MaterialsFragment>().adapter.notifyDataSetChanged()
    }
}
