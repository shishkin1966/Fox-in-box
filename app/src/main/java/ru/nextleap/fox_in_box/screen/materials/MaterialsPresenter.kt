package ru.nextleap.fox_in_box.screen.materials

import com.google.gson.internal.LinkedTreeMap
import ru.nextleap.common.ApplicationUtils
import ru.nextleap.fox_in_box.action.Actions
import ru.nextleap.fox_in_box.data.BaseResponse
import ru.nextleap.fox_in_box.data.Materials
import ru.nextleap.fox_in_box.provider.Providers
import ru.nextleap.fox_in_box.request.GetMaterialsListRequest
import ru.nextleap.sl.action.*
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
        if (!::data.isInitialized) {
            data = MaterialsData()
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
            getView<MaterialsFragment>().addAction(ShowProgressBarAction())
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
        getView<MaterialsFragment>().addAction(ApplicationAction(Actions.ClearItems))
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
                                getView<MaterialsFragment>().addAction(
                                    DataAction(
                                        Actions.AddItems,
                                        list
                                    )
                                )
                            } else {
                                eof = true
                            }
                            if (eof) {
                                getView<MaterialsFragment>().addAction(HideProgressBarAction())
                            }
                            hasData()
                        } else {
                            getView<MaterialsFragment>().addAction(HideProgressBarAction())
                        }
                    }
                }
            } else {
                getView<MaterialsFragment>().addAction(HideProgressBarAction())
                getView<MaterialsFragment>().addAction(ShowErrorAction(result.getErrorText()))
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
                        getView<MaterialsFragment>().dataChanged()
                    }
                    return true
                }
            }
        }
        return false
    }
}
