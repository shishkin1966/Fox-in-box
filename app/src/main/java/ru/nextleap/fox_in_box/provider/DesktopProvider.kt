package ru.nextleap.fox_in_box.provider

import android.content.Context
import ru.nextleap.common.ApplicationUtils.Companion.getResourceId
import ru.nextleap.sl.AbsProvider
import ru.nextleap.sl.IProvider
import ru.nextleap.sl.provider.ApplicationProvider


class DesktopProvider : AbsProvider(), IDesktopProvider {
    private var desktop: String = ""
    private var items: List<String> =
        listOf("news", "profile", "prize", "materials", "more")

    companion object {
        const val NAME = "DesktopProvider"
    }

    override fun getLayoutId(name: String, defaultId: Int): Int {
        return getResourceId(name, "layout", defaultId)
    }

    override fun getColorId(name: String, defaultId: Int): Int {
        return getResourceId(name, "color", defaultId)
    }

    override fun getStyleId(name: String, defaultId: Int): Int {
        return getResourceId(name, "style", defaultId)
    }

    override fun getMenuId(name: String, defaultId: Int): Int {
        return getResourceId(name, "menu", defaultId)
    }

    override fun getResourceId(name: String, type: String, defaultId: Int): Int {
        val context: Context = ApplicationProvider.appContext
        var resource = name
        if (desktop.isNotEmpty()) {
            resource += "_$desktop"
        }
        val resId = getResourceId(context, type, resource)
        if (resId != 0) {
            return resId
        }
        return defaultId
    }

    override fun setDesktop(desktop: String) {
        this.desktop = desktop
    }

    override fun getDesktop(): String {
        return desktop
    }

    override fun getItems(): List<String> {
        return items
    }

    override fun setItems(items: List<String>) {
        this.items = items
    }

    override fun getName(): String {
        return NAME
    }

    override operator fun compareTo(other: IProvider): Int {
        return if (other is IDesktopProvider) 0 else 1
    }


}