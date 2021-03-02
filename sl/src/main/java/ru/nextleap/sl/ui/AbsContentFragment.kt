package ru.nextleap.sl.ui

import ru.nextleap.sl.action.IAction
import ru.nextleap.sl.action.handler.FragmentActionHandler

abstract class AbsContentFragment() : AbsModelFragment(), OnBackPressListener {

    val actionHandler = FragmentActionHandler(this)

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (actionHandler.onAction(action)) return true

        return false
    }

    /**
     * @return true if fragment itself or its children correctly handle back press event.
     */
    override fun onBackPressed(): Boolean {
        var backPressedHandled = false

        val fragmentManager = childFragmentManager
        val children = fragmentManager.fragments
        for (child in children) {
            if (child != null && (child is OnBackPressListener)) {
                backPressedHandled =
                    backPressedHandled or (child as OnBackPressListener).onBackPressed()
            }
        }
        return backPressedHandled
    }

    override fun isTop(): Boolean {
        return false
    }
}