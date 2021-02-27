package ru.nextleap.sl.ui

interface IPermissionListener {
    /**
     * Событие - право разрешено
     *
     * @param permission право
     */
    fun onPermissionGranted(permission: String)

    /**
     * Событие - право запрещено
     *
     * @param permission право
     */
    fun onPermissionDenied(permission: String)
}
