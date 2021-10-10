package ru.nextleap.fox_in_box.screen.products

import com.google.gson.internal.LinkedTreeMap
import ru.nextleap.common.ApplicationUtils
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.action.Actions
import ru.nextleap.fox_in_box.data.BaseResponse
import ru.nextleap.fox_in_box.data.SKU
import ru.nextleap.fox_in_box.provider.Providers
import ru.nextleap.fox_in_box.request.GetSKUListRequest
import ru.nextleap.fox_in_box.request.PutStorageRequest
import ru.nextleap.sl.Pager
import ru.nextleap.sl.action.ApplicationAction
import ru.nextleap.sl.action.IAction
import ru.nextleap.sl.action.ShowErrorAction
import ru.nextleap.sl.data.ExtResult
import ru.nextleap.sl.presenter.AbsModelPresenter
import ru.nextleap.sl.request.IResponseListener

@Suppress("UNCHECKED_CAST")
class ProductsPresenter(model: ProductsModel) : AbsModelPresenter(model), IResponseListener {

    companion object {
        const val NAME = "ProductsPresenter"
        const val PageSize = 5
    }

    private lateinit var data: SKUData
    private val pager = Pager()

    override fun isRegister(): Boolean {
        return false
    }

    override fun getName(): String {
        return NAME
    }

    override fun onStart() {
        pager.setPageSize(PageSize)
        if (!this::data.isInitialized) {
            val json = ApplicationSingleton.instance.storageProvider.get(NAME)
            if (json == null) {
                data = SKUData()
                getData()
            } else {
                data = ApplicationUtils.fromJson(json.toString(), SKUData::class.java)
                getModel<ProductsModel>().addAllItems(data.list)
            }
        }
    }

    private fun getData() {
        init()
        ApplicationUtils.runOnUiThread {
            getModel<ProductsModel>().getHandler().showProgressBar()
        }
        hasData()
    }

    private fun init() {
        pager.init()
        data.list.clear()
        getModel<ProductsModel>().clearItems()
    }

    private fun hasData() {
        if (!pager.eof) {
            Providers.getSKUList(getName(), pager.currentPosition, pager.nextPageSize)
        }
    }

    override fun response(result: ExtResult) {
        ApplicationUtils.runOnUiThread {
            if (!result.hasError()) {
                when (result.getName()) {
                    GetSKUListRequest.NAME -> {
                        val data = result.getData() as BaseResponse
                        if (data.code == 0) {
                            val list: ArrayList<SKU> = ArrayList()
                            if (data.result is LinkedTreeMap<*, *>) {
                                val items = (data.result as LinkedTreeMap<*, *>).get("data")
                                for (item in items as List<*>) {
                                    list.add(SKU(item as LinkedTreeMap<String, Any?>))
                                }
                            }
                            if (list.isNotEmpty()) {
                                pager.add(list.size)
                                this.data.list.addAll(list)
                                getModel<ProductsModel>().addItems(list)
                            } else {
                                pager.eof = true
                            }
                            if (pager.eof) {
                                val json = ApplicationUtils.toJson(this.data)
                                ApplicationSingleton.instance.commonExecutor.execute(
                                    PutStorageRequest(NAME, json)
                                )
                                getModel<ProductsModel>().getHandler().hideProgressBar()
                            }
                            hasData()
                        } else {
                            getModel<ProductsModel>().getHandler().hideProgressBar()
                        }
                    }
                }
            } else {
                getModel<ProductsModel>().getHandler().hideProgressBar()
                getModel<ProductsModel>().getHandler().showErrorAction(ShowErrorAction(result.getErrorText()))
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
                        getModel<ProductsModel>().dataChanged()
                    }
                    return true
                }
            }
        }
        return false
    }

}
