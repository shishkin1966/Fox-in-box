package ru.nextleap.common.recyclerview

import androidx.recyclerview.widget.RecyclerView

abstract class AbsComparableRecyclerViewAdapter<E : Comparable<E>, VH : RecyclerView.ViewHolder> :
    IRecyclerViewAdapter<E>,
    RecyclerView.Adapter<VH>() {
    private var items: MutableList<E> = ArrayList()

    override fun getItemCount(): Int = items.size

    override fun setItem(position: Int, item: E) {
        this.items[position] = item
        notifyItemChanged(position)
    }

    override fun setItems(items: List<E>) {
        this.items.clear()
        val sorted = items.sorted()
        this.items = ArrayList()
        this.items.addAll(sorted)
        notifyDataSetChanged()
    }

    override fun add(e: E) {
        this.items.add(e)
        val sorted = this.items.sorted()
        this.items = ArrayList()
        this.items.addAll(sorted)
        notifyDataSetChanged()
    }

    override fun add(position: Int, e: E) {
        this.items.add(position, e)
        val sorted = this.items.sorted()
        this.items = ArrayList()
        this.items.addAll(sorted)
        notifyDataSetChanged()
    }

    override fun addAll(items: List<E>) {
        this.items.addAll(items)
        val sorted = this.items.sorted()
        this.items = ArrayList()
        this.items.addAll(sorted)
        notifyDataSetChanged()
    }

    override fun move(fromPosition: Int, toPosition: Int) {
    }

    override fun remove(position: Int): E {
        val e = this.items.removeAt(position)
        notifyDataSetChanged()
        return e
    }

    override fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun isEmpty(): Boolean {
        return items.isEmpty()
    }

    override fun getItem(position: Int): E {
        return items[position]
    }

    override fun getItems(): List<E> {
        return items
    }

}
