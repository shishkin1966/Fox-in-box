package ru.nextleap.fox_in_box.action

import android.widget.ImageView
import ru.nextleap.sl.action.AbsAction
import ru.nextleap.sl.action.IAction
import ru.nextleap.sl.request.Rank
import java.lang.ref.WeakReference

class ImageAction(
    view: ImageView,
    private var url: String,
    private var width: Int,
    private var height: Int
) : AbsAction(),
    IAction {
    private var rank: Int = Rank.MIN_RANK
    private var view: WeakReference<ImageView>? = null
    private var isWithCache = true
    private var placeHolder = -1
    private var errorHolder = -1

    init {
        this.view = WeakReference(view)
    }

    fun getView(): ImageView? {
        return view?.get()
    }

    fun getUrl(): String {
        return url
    }

    fun getRank(): Int {
        return rank
    }

    fun setRank(rank: Int): ImageAction {
        this.rank = rank
        return this
    }

    fun isWithCache(): Boolean {
        return isWithCache
    }

    fun setWithCache(withCache: Boolean): ImageAction {
        isWithCache = withCache
        return this
    }

    fun getPlaceHolder(): Int {
        return placeHolder
    }

    fun setPlaceHolder(placeholder: Int): ImageAction {
        placeHolder = placeholder
        return this
    }

    fun getErrorHolder(): Int {
        return errorHolder
    }

    fun setErrorHolder(errorHolder: Int): ImageAction {
        this.errorHolder = errorHolder
        return this
    }

    fun getWidth(): Int {
        return width
    }

    fun getHeight(): Int {
        return height
    }
}