package ru.nextleap.common.recyclerview

interface IRecyclerViewAdapter<E> {
    fun getItemCount(): Int

    fun setItems(items: List<E>)

    fun add(e: E)

    fun add(position: Int, e: E)

    fun addAll(items: List<E>)

    fun move(fromPosition: Int, toPosition: Int)

    fun remove(position: Int): E

    fun clear()

    fun isEmpty(): Boolean

    fun getItem(position: Int): E

    fun getItems(): List<E>
}
