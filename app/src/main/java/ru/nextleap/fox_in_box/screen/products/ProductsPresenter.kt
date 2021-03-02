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
            data = SKUData()
            getData()
        } else {
            data = ApplicationSingleton.instance.storageProvider.fromJson(json.toString(), SKUData::class.java)
            getView<ProductsFragment>().addAllItems(data.list)
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
            getView<ProductsFragment>().actionHandler.showProgressBar()
        }
        hasData()
    }

    private fun init() {
        currentPosition = 0
        currentPageSize = 0
        eof = false
        data.list.clear()
        getView<ProductsFragment>().clearItems()
    }

    private fun hasData() {
        if (!eof) {
            Providers.getSKUList(getName(), currentPosition, nextPageSize)
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
                                currentPosition += list.size
                                if (list.size < currentPageSize) {
                                    eof = true
                                }
                                this.data.list.addAll(list)
                                getView<ProductsFragment>().addItems(list)
                            } else {
                                eof = true
                            }
                            if (eof) {
                                val json = ApplicationSingleton.instance.storageProvider.toJson(this.data)
                                ApplicationSingleton.instance.commonExecutor.execute(
                                    PutStorageRequest(NAME, json)
                                )
                                getView<ProductsFragment>().actionHandler.hideProgressBar()
                            }
                            hasData()
                        } else {
                            getView<ProductsFragment>().actionHandler.hideProgressBar()
                        }
                    }
                }
            } else {
                getView<ProductsFragment>().actionHandler.hideProgressBar()
                getView<ProductsFragment>().actionHandler.showErrorAction(ShowErrorAction(result.getErrorText()))
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
                        getView<ProductsFragment>().dataChanged()
                    }
                    return true
                }
            }
        }
        return false
    }

}
