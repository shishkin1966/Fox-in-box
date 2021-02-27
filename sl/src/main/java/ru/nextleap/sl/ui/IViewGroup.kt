package ru.nextleap.sl.ui

import android.view.View
import androidx.annotation.IdRes

interface IViewGroup {
    /**
     * Найти view
     *
     * @param <V> the type view
     * @param id  the id view
     * @return the view
    </V> */
    fun <V : View> findView(@IdRes id: Int): V?

    /**
     * Получить корневой view
     *
     * @return the view
     */
    fun getRootView(): View?

}