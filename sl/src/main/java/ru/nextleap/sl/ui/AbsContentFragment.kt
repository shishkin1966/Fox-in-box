package ru.nextleap.sl.ui

abstract class AbsContentFragment() : AbsModelFragment(), OnBackPressListener {
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