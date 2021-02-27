package ru.nextleap.fox_in_box.provider

import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.nextleap.common.onlyChar
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.GlideApp
import ru.nextleap.fox_in_box.action.ImageAction
import ru.nextleap.fox_in_box.request.GetImageSizeRequest
import ru.nextleap.sl.AbsProvider
import ru.nextleap.sl.IProvider
import ru.nextleap.sl.PreferencesUtils
import ru.nextleap.sl.provider.ApplicationProvider


class NetImageProvider : AbsProvider(), INetImageProvider {

    companion object {
        const val NAME = "NetImageProvider"
    }

    override fun downloadImage(subscriber: String, action: ImageAction) {
        var isSize = true
        if (PreferencesUtils.getInt(
                ApplicationProvider.appContext,
                action.getUrl().onlyChar() + "_width",
                -1
            ) < 0
        ) {
            //Log.i(NAME,"Not size: " + action.getUrl().onlyChar() + "_width")
            ApplicationSingleton.instance.commonExecutor.execute(
                GetImageSizeRequest(
                    subscriber,
                    action
                )
            )
            isSize = false
        }
        if (isSize) {
            val newWidth = action.getWidth()
            val newHeight = ApplicationSingleton.instance.newImageHeight(
                action.getUrl(),
                action.getWidth()
            )
            GlideApp.with(ApplicationProvider.appContext)
                .load(action.getUrl())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(newWidth, newHeight)
                .dontAnimate()
                .into(action.getView()!!)
        } else {
            GlideApp.with(ApplicationProvider.appContext)
                .load(action.getUrl())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(action.getView()!!)
        }
    }

    override fun clearCache() {
        GlideApp.get(ApplicationProvider.appContext).clearDiskCache()
    }

    override fun compareTo(other: IProvider): Int {
        return if (other is INetImageProvider) 0 else 1
    }

    override fun getName(): String {
        return NAME
    }

}