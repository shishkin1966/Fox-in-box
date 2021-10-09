package ru.nextleap.fox_in_box.screen.materials

import com.google.gson.internal.LinkedTreeMap
import ru.nextleap.common.ApplicationUtils
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.action.Actions
import ru.nextleap.fox_in_box.data.BaseResponse
import ru.nextleap.fox_in_box.data.Materials
import ru.nextleap.fox_in_box.provider.Providers
import ru.nextleap.fox_in_box.request.GetMaterialsListRequest
import ru.nextleap.fox_in_box.request.PutStorageRequest
import ru.nextleap.sl.action.ApplicationAction
import ru.nextleap.sl.action.IAction
import ru.nextleap.sl.action.ShowErrorAction
import ru.nextleap.sl.data.ExtResult
import ru.nextleap.sl.presenter.AbsModelPresenter
import ru.nextleap.sl.request.IResponseListener

@Suppress("UNCHECKED_CAST")
class MaterialsPresenter(model: MaterialsModel) : AbsModelPresenter(model), IResponseListener {

    companion object {
        const val NAME = "MaterialsPresenter"
        const val PageSize = 5
    }

    private lateinit var data: MaterialsData
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

    override fun onStart() {
        setPageSize(PageSize)
        val json = ApplicationSingleton.instance.storageProvider.get(NAME)
        if (json == null) {
            data = MaterialsData()
            getData()
        } else {
            data = ApplicationUtils.fromJson(json.toString(), MaterialsData::class.java)
            getModel<MaterialsModel>().addAllItems(data.list)
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
            getView<MaterialsFragment>().actionHandler.showProgressBar()
        }
        hasData()
    }

    override fun isRegister(): Boolean {
        return true
    }

    override fun getName(): String {
        return NAME
    }

    private fun init() {
        currentPosition = 0
        currentPageSize = 0
        eof = false
        data.list.clear()
        getModel<MaterialsModel>().clearItems()
    }

    private fun hasData() {
        if (!eof) {
            Providers.getMaterialsList(getName(), currentPosition, nextPageSize)
        }
    }

    override fun response(result: ExtResult) {
        ApplicationUtils.runOnUiThread {
            if (!result.hasError()) {
                when (result.getName()) {
                    GetMaterialsListRequest.NAME -> {
                        val data = result.getData() as BaseResponse
                        if (data.code == 0) {
                            val list: ArrayList<Materials> = ArrayList()
                            for (item in data.result as List<*>) {
                                list.add(Materials(item as LinkedTreeMap<String, Any?>))
                            }
                            if (list.isNotEmpty()) {
                                currentPosition += list.size
                                if (list.size < currentPageSize) {
                                    eof = true
                                }
                                this.data.list.addAll(list)
                                getModel<MaterialsModel>().addItems(list)
                            } else {
                                eof = true
                            }
                            if (eof) {
                                val json = ApplicationUtils.toJson(this.data)
                                ApplicationSingleton.instance.commonExecutor.execute(
                                    PutStorageRequest(NAME, json)
                                )
                                getView<MaterialsFragment>().actionHandler.hideProgressBar()
                            }
                            hasData()
                        } else {
                            getView<MaterialsFragment>().actionHandler.hideProgressBar()
                        }
                    }
                }
            } else {
                getView<MaterialsFragment>().actionHandler.hideProgressBar()
                getView<MaterialsFragment>().actionHandler.showErrorAction(ShowErrorAction(result.getErrorText()))
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
                        getModel<MaterialsModel>().dataChanged()
                    }
                    return true
                }
            }
        }
        return false
    }
}
