package ru.nextleap.fox_in_box.request

import android.graphics.Bitmap
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import ru.nextleap.common.onlyChar
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.GlideApp
import ru.nextleap.fox_in_box.action.Actions
import ru.nextleap.fox_in_box.action.ImageAction
import ru.nextleap.sl.PreferencesUtils
import ru.nextleap.sl.action.ApplicationAction
import ru.nextleap.sl.presenter.IPresenter
import ru.nextleap.sl.provider.ApplicationProvider
import ru.nextleap.sl.request.AbsRequest

class GetImageSizeRequest(private val subscriber: String, private val action: ImageAction) :
    AbsRequest() {
    companion object {
        const val NAME = "GetImageSizeRequest"
    }

    override fun isDistinct(): Boolean {
        return false
    }

    override fun run() {
        val url = action.getUrl()

        try {
            GlideApp.with(ApplicationProvider.appContext)
                .asBitmap()
                .load(url)
                .into(object : SimpleTarget<Bitmap?>() {
                    override fun onResourceReady(
                        bitmap: Bitmap,
                        transition: Transition<in Bitmap?>?
                    ) {
                        val width = bitmap.width
                        val height = bitmap.height
                        if (width > 0 && height > 0) {
                            //Log.i(NAME,"Put size: " + action.getUrl().onlyChar() + "_width")
                            PreferencesUtils.putInt(
                                ApplicationProvider.appContext,
                                url.onlyChar() + "_width",
                                width
                            )
                            PreferencesUtils.putInt(
                                ApplicationProvider.appContext,
                                url.onlyChar() + "_height",
                                height
                            )
                            val presenter =
                                ApplicationSingleton.instance.getPresenter<IPresenter>(
                                    subscriber
                                )
                            presenter?.addAction(ApplicationAction(Actions.DataChanged))
                        }
                    }
                })
        } catch (e: Exception) {
        }
    }

    override fun getName(): String {
        return NAME
    }

}