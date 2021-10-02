package ru.nextleap.fox_in_box

import android.content.Context
import android.os.Environment
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class GlideProvider : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            with(builder) {
                setDiskCache(
                    ExternalPreferredCacheDiskCacheFactory(
                        context,
                        "Glide",
                        (1024 * 1024 * 200).toLong()
                    )
                    )
            }  // 200 MB
        } else {
            builder.setDiskCache(
                InternalCacheDiskCacheFactory(
                    context,
                    "Glide",
                    (1024 * 1024 * 50).toLong()
                )
            )  // 50 MB
        }
    }

}