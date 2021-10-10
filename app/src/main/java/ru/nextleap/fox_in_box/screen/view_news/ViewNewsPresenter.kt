package ru.nextleap.fox_in_box.screen.view_news

import com.google.gson.internal.LinkedTreeMap
import ru.nextleap.common.ApplicationUtils
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.R
import ru.nextleap.fox_in_box.action.Actions
import ru.nextleap.fox_in_box.data.BaseResponse
import ru.nextleap.fox_in_box.data.News
import ru.nextleap.fox_in_box.provider.Providers
import ru.nextleap.fox_in_box.request.GetNewsRequest
import ru.nextleap.fox_in_box.screen.news.NewsPresenter
import ru.nextleap.sl.action.*
import ru.nextleap.sl.data.ExtResult
import ru.nextleap.sl.message.DataMessage
import ru.nextleap.sl.presenter.AbsModelPresenter
import ru.nextleap.sl.request.IResponseListener

@Suppress("UNCHECKED_CAST")
class ViewNewsPresenter(model: ViewNewsModel) : AbsModelPresenter(model), IResponseListener {

    companion object {
        const val NAME = "ViewNewsPresenter"
    }

    private var item: News? = null

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (action is DataAction<*>) {
            when (action.getName()) {
                Actions.OnClick -> {
                    onClick(action.getData() as Int)
                    return true
                }
            }
        }
        return false
    }


    override fun isRegister(): Boolean {
        return true
    }

    override fun getName(): String {
        return NAME
    }

    private fun getData() {
        if (item != null) {
            getView<ViewNewsFragment>().addAction(ShowProgressBarAction())
            Providers.getNews(getName(), item!!.Id)
        }
    }

    fun setNews(news: News?) {
        item = news
        getData()
    }

    override fun response(result: ExtResult) {
        ApplicationUtils.runOnUiThread {
            getView<ViewNewsFragment>().addAction(HideProgressBarAction())
            if (!result.hasError()) {
                when (result.getName()) {
                    GetNewsRequest.NAME -> {
                        val data = result.getData() as BaseResponse
                        if (data.code == 0) {
                            if (data.result is LinkedTreeMap<*, *>) {
                                val news = News(data.result as LinkedTreeMap<String, Any?>)
                                news.Image = item!!.Image
                                news.MyVote = item!!.MyVote
                                getView<ViewNewsFragment>().addAction(
                                    DataAction(
                                        Actions.SetItem,
                                        news
                                    )
                                )
                            }
                        }
                    }
                }
            } else {
                getView<ViewNewsFragment>().addAction(ShowErrorAction(result.getErrorText()))
            }
        }
    }

    private fun onClick(id: Int) {
        if (item != null) {
            when (id) {
                R.id.likeImage -> {
                    item!!.MyVote = !(item!!.MyVote)
                    getView<ViewNewsFragment>().addAction(DataAction(Actions.ResponseOnClick, id))
                    ApplicationSingleton.instance.addMessage(
                        DataMessage(
                            NewsPresenter.NAME,
                            item!!
                        )
                    )
                }
            }
        }
    }

    fun getNews(): News? {
        return item
    }

}
