package ru.nextleap.fox_in_box.screen.news

import android.annotation.SuppressLint
import ru.nextleap.fox_in_box.data.News
import ru.nextleap.fox_in_box.screen.IItemsList
import ru.nextleap.sl.model.AbsPresenterModel


class NewsModel(view: NewsFragment) : AbsPresenterModel(view), IItemsList<News> {
    init {
        setPresenter(NewsPresenter(this))
    }

    override fun addAllItems(data: ArrayList<News>) {
        getView<NewsFragment>().adapter.setItems(data)
    }

    override fun addItems(data: ArrayList<News>) {
        getView<NewsFragment>().adapter.addAll(data)
    }

    override fun clearItems() {
        getView<NewsFragment>().adapter.clear()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun dataChanged() {
        getView<NewsFragment>().adapter.notifyDataSetChanged()
    }

}
