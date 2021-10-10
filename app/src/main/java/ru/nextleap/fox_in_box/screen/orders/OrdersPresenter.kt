package ru.nextleap.fox_in_box.screen.orders

import com.google.gson.internal.LinkedTreeMap
import ru.nextleap.common.ApplicationUtils
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.action.Actions
import ru.nextleap.fox_in_box.data.BaseResponse
import ru.nextleap.fox_in_box.data.Orders
import ru.nextleap.fox_in_box.provider.Providers
import ru.nextleap.fox_in_box.request.GetOrdersListRequest
import ru.nextleap.fox_in_box.request.PutStorageRequest
import ru.nextleap.sl.Pager
import ru.nextleap.sl.action.ApplicationAction
import ru.nextleap.sl.action.IAction
import ru.nextleap.sl.action.ShowErrorAction
import ru.nextleap.sl.data.ExtResult
import ru.nextleap.sl.presenter.AbsModelPresenter
import ru.nextleap.sl.request.IResponseListener

class OrdersPresenter(model: OrdersModel) : AbsModelPresenter(model), IResponseListener {

    companion object {
        const val NAME = "OrdersPresenter"
        const val PageSize = 5
    }

    private lateinit var data: OrdersData
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
            data = OrdersData()
            getData()
        } else {
            data = ApplicationUtils.fromJson(json.toString(), OrdersData::class.java)
            getModel<OrdersModel>().addAllItems(data.list)
        }
    }

    private fun getData() {
        init()
        ApplicationUtils.runOnUiThread {
            getModel<OrdersModel>().getHandler().showProgressBar()
        }
        hasData()
    }

    private fun init() {
        pager.init()
        data.list.clear()
        getModel<OrdersModel>().clearItems()
    }

    private fun hasData() {
        if (!pager.eof) {
            Providers.getOrdersList(getName(), pager.currentPosition, pager.nextPageSize)
        }
    }

    override fun response(result: ExtResult) {
        ApplicationUtils.runOnUiThread {
            if (!result.hasError()) {
                when (result.getName()) {
                    GetOrdersListRequest.NAME -> {
                        val data = result.getData() as BaseResponse
                        if (data.code == 0) {
                            val list: ArrayList<Orders> = ArrayList()
                            if (data.result is LinkedTreeMap<*, *>) {
                            }
                            if (list.isNotEmpty()) {
                                pager.add(list.size)
                                this.data.list.addAll(list)
                                getModel<OrdersModel>().addItems(list)
                            } else {
                                pager.eof = true
                            }
                            if (pager.eof) {
                                val json = ApplicationUtils.toJson(this.data)
                                ApplicationSingleton.instance.commonExecutor.execute(
                                    PutStorageRequest(NAME, json)
                                )
                                getModel<OrdersModel>().getHandler().hideProgressBar()
                            }
                            hasData()
                        } else {
                            getModel<OrdersModel>().getHandler().hideProgressBar()
                        }
                    }
                }
            } else {
                getModel<OrdersModel>().getHandler().hideProgressBar()
                getModel<OrdersModel>().getHandler().showErrorAction(ShowErrorAction(result.getErrorText()))
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
                        getModel<OrdersModel>().dataChanged()
                    }
                    return true
                }
            }
        }
        return false
    }

}
