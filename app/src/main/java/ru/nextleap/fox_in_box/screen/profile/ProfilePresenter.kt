package ru.nextleap.fox_in_box.screen.profile

import com.google.gson.internal.LinkedTreeMap
import ru.nextleap.common.ApplicationUtils
import ru.nextleap.fox_in_box.action.Actions
import ru.nextleap.fox_in_box.data.BaseResponse
import ru.nextleap.fox_in_box.data.Profile
import ru.nextleap.fox_in_box.provider.Providers
import ru.nextleap.fox_in_box.request.GetProfileRequest
import ru.nextleap.sl.action.DataAction
import ru.nextleap.sl.action.HideProgressBarAction
import ru.nextleap.sl.action.ShowErrorAction
import ru.nextleap.sl.action.ShowProgressBarAction
import ru.nextleap.sl.data.ExtResult
import ru.nextleap.sl.presenter.AbsModelPresenter
import ru.nextleap.sl.request.IResponseListener

class ProfilePresenter(model: ProfileModel) : AbsModelPresenter(model), IResponseListener {

    companion object {
        const val NAME = "ProfilePresenter"
    }

    private lateinit var data: ProfileData

    override fun isRegister(): Boolean {
        return false
    }

    override fun getName(): String {
        return NAME
    }

    override fun onStart() {
        if (!::data.isInitialized) {
            getData()
        } else {
            getView<ProfileFragment>().addAction(DataAction(Actions.SetItem, data.profile))
        }
    }

    private fun getData() {
        getView<ProfileFragment>().addAction(ShowProgressBarAction())
        Providers.getProfile(getName())
    }

    override fun response(result: ExtResult) {
        ApplicationUtils.runOnUiThread {
            getView<ProfileFragment>().addAction(HideProgressBarAction())
            if (!result.hasError()) {
                when (result.getName()) {
                    GetProfileRequest.NAME -> {
                        val data = result.getData() as BaseResponse
                        if (data.code == 0) {
                            if (data.result is LinkedTreeMap<*, *>) {
                                val profile = Profile(data.result as LinkedTreeMap<String, Any?>)
                                getView<ProfileFragment>().addAction(
                                    DataAction(
                                        Actions.SetItem,
                                        profile
                                    )
                                )
                            }
                        }
                    }
                }
            } else {
                getView<ProfileFragment>().addAction(ShowErrorAction(result.getErrorText()))
            }
        }
    }

}
