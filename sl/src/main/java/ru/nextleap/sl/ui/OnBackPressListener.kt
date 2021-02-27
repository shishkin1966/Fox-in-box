package ru.nextleap.sl.ui

interface OnBackPressListener {
    fun onBackPressed(): Boolean

    fun isTop(): Boolean
}