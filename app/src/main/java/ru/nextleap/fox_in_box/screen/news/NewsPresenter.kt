package ru.nextleap.fox_in_box.screen.news

import com.google.gson.internal.LinkedTreeMap
import ru.nextleap.common.ApplicationUtils
import ru.nextleap.fox_in_box.action.Actions
import ru.nextleap.fox_in_box.data.BaseResponse
import ru.nextleap.fox_in_box.data.News
import ru.nextleap.fox_in_box.provider.Providers
import ru.nextleap.fox_in_box.request.GetNewsListRequest
import ru.nextleap.sl.action.*
import ru.nextleap.sl.data.ExtResult
import ru.nextleap.sl.message.DataMessage
import ru.nextleap.sl.message.IMessage
import ru.nextleap.sl.presenter.AbsModelPresenter
import ru.nextleap.sl.request.IResponseListener

@Suppress("UNCHECKED_CAST")
class NewsPresenter(model: NewsModel) : AbsModelPresenter(model), IResponseListener {

    companion object {
        const val NAME = "NewsPresenter"
        const val PageSize = 5
    }

    private lateinit var data: NewsData
    private var currentPageSize = 0
    private var currentPosition = 0
    private val nextPageSize: Int
        get() {
            for (i in pageSize.indices) {
                if (pageSize[i] > currentPageSize) {
                    currentPageSize = pageSize[i]
                    return pageSize[i]
                }
            }
            return pageSize[pageSize.size - 1]
        }
    private lateinit var pageSize: ArrayList<Int>
    private var eof = false

    override fun isRegister(): Boolean {
        return true
    }

    override fun response(result: ExtResult) {
        ApplicationUtils.runOnUiThread {
            if (!result.hasError()) {
                when (result.getName()) {
                    GetNewsListRequest.NAME -> {
                        val data = result.getData() as BaseResponse
                        if (data.code == 0) {
                            val list: ArrayList<News> = ArrayList()
                            for (item in data.result as List<*>) {
                                list.add(News(item as LinkedTreeMap<String, Any?>))
                            }
                            if (list.isNotEmpty()) {
                                currentPosition += list.size
                                if (list.size < currentPageSize) {
                                    eof = true
                                }
                                this.data.list.addAll(list)
                                getView<NewsFragment>().addAction(
                                    DataAction(
                                        Actions.AddItems,
                                        list
                                    )
                                )
                            } else {
                                eof = true
                            }
                            if (eof) {
                                getView<NewsFragment>().addAction(HideProgressBarAction())
                            }
                            hasData()
                        } else {
                            getView<NewsFragment>().addAction(HideProgressBarAction())
                        }
                    }
                }
            } else {
                getView<NewsFragment>().addAction(HideProgressBarAction())
                getView<NewsFragment>().addAction(ShowErrorAction(result.getErrorText()))
            }
        }
    }

    override fun getName(): String {
        return NAME
    }

    override fun onStart() {
        setPageSize(PageSize)
        if (!::data.isInitialized) {
            data = NewsData()
            getData()
        }
    }

    private fun setPageSize(initialPageSize: Int) {
        if (initialPageSize > 0) {
            pageSize = ArrayList()
            pageSize.add(initialPageSize)
            pageSize.add(initialPageSize * 2)
            pageSize.add(initialPageSize * 4)
        }
    }

    private fun getData() {
        init()
        ApplicationUtils.runOnUiThread {
            getView<NewsFragment>().addAction(ShowProgressBarAction())
        }
        hasData()
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (action is ApplicationAction) {
            when (action.getName()) {
                Actions.OnSwipeRefresh -> {
                    getData()
                    return true
                }

                Actions.DataChanged -> {
                    ApplicationUtils.runOnUiThread {
                        getView<NewsFragment>().dataChanged()
                    }
                    return true
                }
            }
        }
        return false
    }

    private fun init() {
        currentPosition = 0
        currentPageSize = 0
        eof = false
        data.list.clear()
        getView<NewsFragment>().addAction(ApplicationAction(Actions.ClearItems))
    }

    private fun hasData() {
        if (!eof) {
            Providers.getNewsList(getName(), currentPosition, nextPageSize)
        }
    }

    override fun read(message: IMessage) {
        if (message is DataMessage) {
            for (item: News in data.list) {
                if (item.Id == (message.getData() as News).Id) {
                    item.MyVote = (message.getData() as News).MyVote
                    getView<NewsFragment>().dataChanged()
                    break
                }
            }
        }
    }

}
