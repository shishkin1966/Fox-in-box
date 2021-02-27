package ru.nextleap.fox_in_box.screen

interface IItemsList<T> {
    fun addAllItems(data: ArrayList<T>)

    fun addItems(data: ArrayList<T>)

    fun clearItems()
}