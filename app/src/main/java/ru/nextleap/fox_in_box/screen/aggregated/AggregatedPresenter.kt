package ru.nextleap.fox_in_box.screen.aggregated

import com.google.gson.internal.LinkedTreeMap
import ru.nextleap.common.ApplicationUtils
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.action.Actions
import ru.nextleap.fox_in_box.data.Aggregated
import ru.nextleap.fox_in_box.data.BaseResponse
import ru.nextleap.fox_in_box.provider.Providers
import ru.nextleap.fox_in_box.request.GetAggregatedListRequest
import ru.nextleap.fox_in_box.request.PutStorageRequest
import ru.nextleap.sl.Pager
import ru.nextleap.sl.action.ApplicationAction
import ru.nextleap.sl.action.IAction
import ru.nextleap.sl.action.ShowErrorAction
import ru.nextleap.sl.data.ExtResult
import ru.nextleap.sl.presenter.AbsModelPresenter
import ru.nextleap.sl.request.IResponseListener

class AggregatedPresenter(model: AggregatedModel) : AbsModelPresenter(model), IResponseListener {

    companion object {
        const val NAME = "AggregatedPresenter"
        const val PageSize = 5
    }

    private lateinit var data: AggregatedData
    private val pager = Pager()

    override fun isRegister(): Boolean {
        return false
    }

    override fun getName(): String {
        return NAME
    }

    override fun onStart() {
        pager.setPageSize(PageSize)
        val json = ApplicationSingleton.instance.storageProvider.get(NAME)
        if (json == null) {
            data = AggregatedData()
            getData()
        } else {
            data = ApplicationUtils.fromJson(json.toString(), AggregatedData::class.java)
            getModel<AggregatedModel>().addAllItems(data.list)
        }
    }

    private fun getData() {
        init()
        ApplicationUtils.runOnUiThread {
            getModel<AggregatedModel>().getHandler().showProgressBar()
        }
        hasData()
    }

    private fun init() {
        pager.init()
        data.list.clear()
        getModel<AggregatedModel>().clearItems()
    }

    private fun hasData() {
        if (!pager.eof) {
            Providers.getAggregatedList(getName(), pager.currentPosition, pager.nextPageSize)
        }
    }

    override fun response(result: ExtResult) {
        ApplicationUtils.runOnUiThread {
            if (!result.hasError()) {
                when (result.getName()) {
                    GetAggregatedListRequest.NAME -> {
                        val data = result.getData() as BaseResponse
                        if (data.code == 0) {
                            val list: ArrayList<Aggregated> = ArrayList()
                            if (data.result is LinkedTreeMap<*, *>) {
                                val items = (data.result as LinkedTreeMap<*, *>).get("data")
                                if (items is LinkedTreeMap<*, *>) {
                                    //val itemsList = items["list"]
                                    //val rewardTypes = items["rewardTypes"]
                                }
                            }
                            if (list.isNotEmpty()) {
                                pager.add(list.size)
                                this.data.list.addAll(list)
                                getModel<AggregatedModel>().addItems(list)
                            } else {
                                pager.eof = true
                            }
                            if (pager.eof) {
                                val json = ApplicationUtils.toJson(this.data)
                                ApplicationSingleton.instance.commonExecutor.execute(
                                    PutStorageRequest(NAME, json)
                                )
                                getModel<AggregatedModel>().getHandler().hideProgressBar()
                            }
                            hasData()
                        } else {
                            getModel<AggregatedModel>().getHandler().hideProgressBar()
                        }
                    }
                }
            } else {
                getModel<AggregatedModel>().getHandler().hideProgressBar()
                getModel<AggregatedModel>().getHandler().showErrorAction(ShowErrorAction(result.getErrorText()))
            }
        }
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
                        getModel<AggregatedModel>().dataChanged()
                    }
                    return true
                }
            }
        }
        return false
    }

}
