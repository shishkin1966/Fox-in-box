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
import ru.nextleap.sl.action.*
import ru.nextleap.sl.data.ExtResult
import ru.nextleap.sl.presenter.AbsModelPresenter
import ru.nextleap.sl.request.IResponseListener
import java.io.Serializable

class AggregatedPresenter(model: AggregatedModel) : AbsModelPresenter(model), IResponseListener {

    companion object {
        const val NAME = "AggregatedPresenter"
        const val PageSize = 5
    }

    private lateinit var data: AggregatedData
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
        return false
    }

    override fun getName(): String {
        return NAME
    }

    override fun onStart() {
        setPageSize(PageSize)
        val json = ApplicationSingleton.instance.storageProvider.get(NAME)
        if  (json == null) {
            data = AggregatedData()
            getData()
        } else {
            data = ApplicationUtils.fromJson(json.toString(), AggregatedData::class.java)
            getView<AggregatedFragment>().addAllItems(data.list)
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
            getView<AggregatedFragment>().actionHandler.showProgressBar()
        }
        hasData()
    }

    private fun init() {
        currentPosition = 0
        currentPageSize = 0
        eof = false
        data.list.clear()
        getView<AggregatedFragment>().clearItems()
    }

    private fun hasData() {
        if (!eof) {
            Providers.getAggregatedList(getName(), currentPosition, nextPageSize)
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
                                    val itemsList = items["list"]
                                    val rewardTypes = items["rewardTypes"]
                                }
                            }
                            if (list.isNotEmpty()) {
                                currentPosition += list.size
                                if (list.size < currentPageSize) {
                                    eof = true
                                }
                                this.data.list.addAll(list)
                                getView<AggregatedFragment>().addItems(list)
                            } else {
                                eof = true
                            }
                            if (eof) {
                                val json = ApplicationUtils.toJson(this.data)
                                ApplicationSingleton.instance.commonExecutor.execute(
                                    PutStorageRequest(NAME, json)
                                )
                                getView<AggregatedFragment>().actionHandler.hideProgressBar()
                            }
                            hasData()
                        } else {
                            getView<AggregatedFragment>().actionHandler.hideProgressBar()
                        }
                    }
                }
            } else {
                getView<AggregatedFragment>().actionHandler.hideProgressBar()
                getView<AggregatedFragment>().actionHandler.showErrorAction(ShowErrorAction(result.getErrorText()))
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
                        getView<AggregatedFragment>().dataChanged()
                    }
                    return true
                }
            }
        }
        return false
    }

}
