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
import ru.nextleap.sl.Pager
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
    private val pager = Pager()

    override fun onStart() {
        pager.setPageSize(PageSize)
        if (!this::data.isInitialized) {
            val json = ApplicationSingleton.instance.storageProvider.get(NAME)
            if (json == null) {
                data = MaterialsData()
                getData()
            } else {
                data = ApplicationUtils.fromJson(json.toString(), MaterialsData::class.java)
                getModel<MaterialsModel>().addAllItems(data.list)
            }
        }
    }

    private fun getData() {
        init()
        ApplicationUtils.runOnUiThread {
            getModel<MaterialsModel>().getHandler().showProgressBar()
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
        pager.init()
        data.list.clear()
        getModel<MaterialsModel>().clearItems()
    }

    private fun hasData() {
        if (!pager.eof) {
            Providers.getMaterialsList(getName(), pager.currentPosition, pager.nextPageSize)
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
                                pager.add(list.size)
                                this.data.list.addAll(list)
                                getModel<MaterialsModel>().addItems(list)
                            } else {
                                pager.eof = true
                            }
                            if (pager.eof) {
                                val json = ApplicationUtils.toJson(this.data)
                                ApplicationSingleton.instance.commonExecutor.execute(
                                    PutStorageRequest(NAME, json)
                                )
                                getModel<MaterialsModel>().getHandler().hideProgressBar()
                            }
                            hasData()
                        } else {
                            getModel<MaterialsModel>().getHandler().hideProgressBar()
                        }
                    }
                }
            } else {
                getModel<MaterialsModel>().getHandler().hideProgressBar()
                getModel<MaterialsModel>().getHandler().showErrorAction(ShowErrorAction(result.getErrorText()))
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
