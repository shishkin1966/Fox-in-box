package ru.nextleap.fox_in_box.screen.news

import com.google.gson.internal.LinkedTreeMap
import ru.nextleap.common.ApplicationUtils
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.action.Actions
import ru.nextleap.fox_in_box.data.BaseResponse
import ru.nextleap.fox_in_box.data.News
import ru.nextleap.fox_in_box.provider.Providers
import ru.nextleap.fox_in_box.request.GetNewsListRequest
import ru.nextleap.fox_in_box.request.PutStorageRequest
import ru.nextleap.sl.Pager
import ru.nextleap.sl.action.ApplicationAction
import ru.nextleap.sl.action.IAction
import ru.nextleap.sl.action.ShowErrorAction
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
    private val pager = Pager()

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
                                pager.add(list.size)
                                this.data.list.addAll(list)
                                getModel<NewsModel>().addItems(list)
                            } else {
                                pager.eof = true
                            }
                            if (pager.eof) {
                                saveData()
                                getModel<NewsModel>().getHandler().hideProgressBar()
                            }
                            hasData()
                        } else {
                            getModel<NewsModel>().getHandler().hideProgressBar()
                        }
                    }
                }
            } else {
                getModel<NewsModel>().getHandler().hideProgressBar()
                getModel<NewsModel>().getHandler().showErrorAction(ShowErrorAction(result.getErrorText()))
            }
        }
    }

    override fun getName(): String {
        return NAME
    }

    override fun onStart() {
        pager.setPageSize(PageSize)
        if (!this::data.isInitialized) {
            val json = ApplicationSingleton.instance.storageProvider.get(NAME)
            if (json == null) {
                data = NewsData()
                getData()
            } else {
                data = ApplicationUtils.fromJson(json.toString(), NewsData::class.java)
                getModel<NewsModel>().addAllItems(data.list)
            }
        }
    }

    private fun getData() {
        init()
        ApplicationUtils.runOnUiThread {
            getModel<NewsModel>().getHandler().showProgressBar()
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
                        getModel<NewsModel>().dataChanged()
                    }
                    return true
                }
            }
        }
        return false
    }

    private fun init() {
        pager.init()
        data.list.clear()
        getModel<NewsModel>().clearItems()
    }

    private fun hasData() {
        if (!pager.eof) {
            Providers.getNewsList(getName(), pager.currentPosition, pager.nextPageSize)
        }
    }

    override fun read(message: IMessage) {
        if (message is DataMessage) {
            val messageItem = message.getData() as News
            for (item: News in data.list) {
                if (item.Id == messageItem.Id) {
                    item.MyVote = messageItem.MyVote
                    getView<NewsFragment>().adapter.setItem(messageItem)
                    saveData()
                    break
                }
            }
        }
    }

    private fun saveData() {
        val json = ApplicationUtils.toJson(this.data)
        ApplicationSingleton.instance.commonExecutor.execute(
            PutStorageRequest(NAME, json)
        )
    }

}
